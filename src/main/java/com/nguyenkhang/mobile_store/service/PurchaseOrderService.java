package com.nguyenkhang.mobile_store.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.mobile_store.dto.request.PurchaseOrderItemRequest;
import com.nguyenkhang.mobile_store.dto.request.PurchaseOrderRequest;
import com.nguyenkhang.mobile_store.dto.response.SimplePurchaseOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderReportResponse;
import com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderResponse;
import com.nguyenkhang.mobile_store.entity.*;
import com.nguyenkhang.mobile_store.enums.InventoryReferenceType;
import com.nguyenkhang.mobile_store.enums.PurchaseOrderStatus;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.PurchaseOrderMapper;
import com.nguyenkhang.mobile_store.repository.*;
import com.nguyenkhang.mobile_store.specification.PurchaseOrderSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseOrderService {
    PurchaseOrderRepository purchaseOrderRepository;
    PurchaseOrderItemRepository itemRepository;
    PurchaseOrderMapper purchaseOrderMapper;
    SupplierRepository supplierRepository;
    WarehouseRepository warehouseRepository;
    UserRepository userRepository;

    ProductVariantRepository variantRepository;
    InventoryRepository inventoryRepository;
    InventoryTransactionRepository inventoryTransactionRepository;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public SimplePurchaseOrderResponse create(PurchaseOrderRequest request) {
        var supplier = supplierRepository
                .findById(request.getSupplierId())
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_EXISTED));
        var warehouse = warehouseRepository
                .findById(request.getWarehouseId())
                .orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Set<Long> variantIds = request.getPurchaseOrderItems().stream()
                .map(PurchaseOrderItemRequest::getProductVariantId)
                .collect(Collectors.toSet());

        Map<Long, ProductVariant> variantMap = variantRepository.findAllById(variantIds).stream()
                .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .warehouse(warehouse)
                .supplier(supplier)
                .totalAmount(BigDecimal.valueOf(0))
                .createBy(user)
                .build();

        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();

        for (var itemRequest : request.getPurchaseOrderItems()) {
            var variant = variantMap.get(itemRequest.getProductVariantId());

            if (variant == null) {
                throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED);
            }
            BigDecimal price = itemRequest.getPricePerUnit();
            BigDecimal quantity = BigDecimal.valueOf(itemRequest.getQuantity());
            BigDecimal total = price.multiply(quantity);
            PurchaseOrderItem purchaseOrderItem = PurchaseOrderItem.builder()
                    .product(variant.getProduct())
                    .productVariant(variant)
                    .purchaseOrder(purchaseOrder)
                    .pricePerUnit(price)
                    .quantity(itemRequest.getQuantity())
                    .total(total)
                    .build();

            purchaseOrderItems.add(purchaseOrderItem);
        }

        purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);

        BigDecimal totalAmount =
                purchaseOrderItems.stream().map(PurchaseOrderItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        purchaseOrder.setTotalAmount(totalAmount);

        try {
            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        } catch (Exception e) {
            log.error("Error creating purchase order", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR);
        }

        log.info(
                "Creating purchase order for supplier {} with {} items", supplier.getName(), purchaseOrderItems.size());

        return purchaseOrderMapper.toSimplePurchaseOrderResponse(purchaseOrder);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<SimplePurchaseOrderResponse> get(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var purchaseOrders = purchaseOrderRepository.findAll(pageable);

        return purchaseOrders.map(purchaseOrderMapper::toSimplePurchaseOrderResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public PurchaseOrderResponse getById(long id) {
        var purchaseOrder = purchaseOrderRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_ORDER_NOT_EXISTED));
        return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public PurchaseOrderResponse approve(long id) {
        var purchaseOrder = purchaseOrderRepository
                .findByIdForUpdate(id)
                .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_ORDER_NOT_EXISTED));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Map<Long, ProductVariant> variantMap = purchaseOrder.getPurchaseOrderItems().stream()
                .map(PurchaseOrderItem::getProductVariant)
                .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

        Map<Long, Inventory> inventoriesByVariants =
                inventoryRepository
                        .findAllByWarehouseIdForUpdate(
                                purchaseOrder.getWarehouse().getId())
                        .stream()
                        .collect(Collectors.toMap((e) -> e.getProductVariant().getId(), Function.identity()));

        List<InventoryTransaction> inventoryTransactionsToCreate = new ArrayList<>();
        List<Inventory> inventoriesToUpdateOrCreate = new ArrayList<>();
        List<ProductVariant> variantToUpdate = new ArrayList<>();

        if (!purchaseOrder.getStatus().equals(PurchaseOrderStatus.DRAFT)) {
            throw new AppException(ErrorCode.STATUS_IS_NOT_CHANGE_TO_APPROVED);
        }

        for (var item : purchaseOrder.getPurchaseOrderItems()) {
            var variant = variantMap.get(item.getProductVariant().getId());

            if (variant == null) {
                throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED);
            }
            Inventory inventory = inventoriesByVariants.get(variant.getId());
            if (inventory != null) {
                inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
                inventory.setUpdateBy(user);

            } else {
                inventory = Inventory.builder()
                        .warehouse(purchaseOrder.getWarehouse())
                        .productVariant(variant)
                        .quantity(item.getQuantity())
                        .createBy(user)
                        .build();
            }

            InventoryTransaction inventoryTransaction = InventoryTransaction.builder()
                    .warehouse(purchaseOrder.getWarehouse())
                    .productVariant(variant)
                    .product(variant.getProduct())
                    .createBy(user)
                    .quantityChange(item.getQuantity())
                    .type(InventoryReferenceType.IMPORT_FROM_SUPPLIER)
                    .note("Import products from suppliers")
                    .referenceId(purchaseOrder.getId())
                    .build();

            variant.setQuantity(variant.getQuantity() + item.getQuantity());

            inventoriesToUpdateOrCreate.add(inventory);
            inventoryTransactionsToCreate.add(inventoryTransaction);
            variantToUpdate.add(variant);
        }

        purchaseOrder.setStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder.setUpdateBy(user);

        try {
            inventoryRepository.saveAll(inventoriesToUpdateOrCreate);
            variantRepository.saveAll(variantToUpdate);
        } catch (ObjectOptimisticLockingFailureException exception) {
            throw new AppException(ErrorCode.CONCURRENT_MODIFICATION);
        }
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        inventoryTransactionRepository.saveAll(inventoryTransactionsToCreate);

        log.info(
                "Approved purchase order {} by user {} with {} items",
                purchaseOrder.getId(),
                user.getUsername(),
                purchaseOrder.getPurchaseOrderItems().size());
        return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public PurchaseOrderResponse cancel(long id) {
        var purchaseOrder = purchaseOrderRepository
                .findByIdForUpdate(id)
                .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_ORDER_NOT_EXISTED));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (purchaseOrder.getStatus().equals(PurchaseOrderStatus.CANCELLED)) {
            throw new AppException(ErrorCode.STATUS_IS_NOT_CHANGE_TO_CANCEL);
        }

        if (purchaseOrder.getStatus().equals(PurchaseOrderStatus.APPROVED)) {
            Map<Long, ProductVariant> variantMap = purchaseOrder.getPurchaseOrderItems().stream()
                    .map(PurchaseOrderItem::getProductVariant)
                    .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

            Map<Long, Inventory> inventoriesByVariants =
                    inventoryRepository
                            .findAllByWarehouseIdForUpdate(
                                    purchaseOrder.getWarehouse().getId())
                            .stream()
                            .collect(Collectors.toMap(
                                    (e) -> e.getProductVariant().getId(), Function.identity()));

            List<InventoryTransaction> inventoryTransactionsToCreate = new ArrayList<>();
            List<Inventory> inventoriesToUpdateOrCreate = new ArrayList<>();
            List<ProductVariant> variantToUpdate = new ArrayList<>();

            for (var item : purchaseOrder.getPurchaseOrderItems()) {
                var variant = variantMap.get(item.getProductVariant().getId());

                if (variant == null) {
                    throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED);
                }

                Inventory inventory = inventoriesByVariants.get(variant.getId());
                if (inventory != null) {
                    inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                    inventory.setUpdateBy(user);
                    inventoriesToUpdateOrCreate.add(inventory);
                }
                InventoryTransaction inventoryTransaction = InventoryTransaction.builder()
                        .warehouse(purchaseOrder.getWarehouse())
                        .productVariant(variant)
                        .product(variant.getProduct())
                        .createBy(user)
                        .quantityChange(-item.getQuantity())
                        .type(InventoryReferenceType.CANCEL_IMPORT_ORDER)
                        .note("Cancel import order")
                        .referenceId(purchaseOrder.getId())
                        .build();

                variant.setQuantity(variant.getQuantity() - item.getQuantity());

                inventoryTransactionsToCreate.add(inventoryTransaction);
                variantToUpdate.add(variant);
            }

            try {
                inventoryRepository.saveAll(inventoriesToUpdateOrCreate);
                variantRepository.saveAll(variantToUpdate);

            } catch (ObjectOptimisticLockingFailureException exception) {
                throw new AppException(ErrorCode.CONCURRENT_MODIFICATION);
            }

            inventoryTransactionRepository.saveAll(inventoryTransactionsToCreate);
        }
        purchaseOrder.setStatus(PurchaseOrderStatus.CANCELLED);
        purchaseOrder.setUpdateBy(user);

        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        log.info(
                "Cancel purchase order {} by user {} with {} items",
                purchaseOrder.getId(),
                user.getUsername(),
                purchaseOrder.getPurchaseOrderItems().size());
        return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delete(long id) {
        var purchaseOrder = purchaseOrderRepository
                .findByIdForUpdate(id)
                .orElseThrow(() -> new AppException(ErrorCode.PURCHASE_ORDER_NOT_EXISTED));
        if (!purchaseOrder.getStatus().equals(PurchaseOrderStatus.DRAFT)) {
            throw new AppException(ErrorCode.DELETE_FAIL);
        }

        purchaseOrderRepository.delete(purchaseOrder);

        return "Purchase order has been deleted";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public List<PurchaseOrderReportResponse> report(LocalDateTime start, LocalDateTime end, String groupBy) {
        return switch (groupBy.toLowerCase()) {
            case "supplier" -> purchaseOrderRepository.reportBySupplier(start, end);
            case "month" -> purchaseOrderRepository.reportByMonth(start, end);
            case "quarter" -> purchaseOrderRepository.reportByQuarter(start, end);

            default -> throw new AppException(ErrorCode.INVALID_GROUP_BY);
        };
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<SimplePurchaseOrderResponse> searchPurchaseOrder(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return purchaseOrderRepository
                .findAll(PurchaseOrderSpecification.createSpecification(keyword), pageable)
                .map(purchaseOrderMapper::toSimplePurchaseOrderResponse);
    }
}
