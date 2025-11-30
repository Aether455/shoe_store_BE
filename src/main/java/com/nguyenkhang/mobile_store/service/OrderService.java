package com.nguyenkhang.mobile_store.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.nguyenkhang.mobile_store.dto.response.order.OrderResponseForCustomer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.mobile_store.dto.Coordinates;
import com.nguyenkhang.mobile_store.dto.request.order.OrderCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderItemRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateStatusRequest;
import com.nguyenkhang.mobile_store.dto.response.order.OrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.*;
import com.nguyenkhang.mobile_store.enums.InventoryReferenceType;
import com.nguyenkhang.mobile_store.enums.OrderStatus;
import com.nguyenkhang.mobile_store.enums.VoucherStatus;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.OrderMapper;
import com.nguyenkhang.mobile_store.mapper.PaymentMapper;
import com.nguyenkhang.mobile_store.repository.*;
import com.nguyenkhang.mobile_store.specification.OrderSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    UserRepository userRepository;
    CustomerRepository customerRepository;
    VoucherRepository voucherRepository;
    InventoryRepository inventoryRepository;
    WarehouseRepository warehouseRepository;
    InventoryTransactionRepository inventoryTransactionRepository;

    ProductVariantRepository variantRepository;
    OrderRepository orderRepository;
    OrderStatusHistoryRepository orderStatusHistoryRepository;
    OrderMapper orderMapper;
    PaymentMapper paymentMapper;

    UserService userService;
    GeocodingService geocodingService;

    @Transactional //  bỏ qua
    public OrderResponse createOrder(OrderCreationRequest request) {


        User user = userService.getCurrentUser();

        Customer customer = customerRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));
        Voucher voucher =
                voucherRepository.findByVoucherCode(request.getVoucherCode()).orElse(null);

        Set<Long> variantIds = request.getOrderItems().stream()
                .map(OrderItemRequest::getProductVariantId)
                .collect(Collectors.toSet());

        Map<Long, ProductVariant> productVariantMap = variantRepository.findAllById(variantIds).stream()
                .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

        List<OrderItem> orderItemList = new ArrayList<>();

        Order order = orderMapper.toOrder(request);

        for (var itemRequest : request.getOrderItems()) {
            var variant = productVariantMap.get(itemRequest.getProductVariantId());
            int quantityToBuy = itemRequest.getQuantity();

            if (variant == null) {
                throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED);
            }

            if (variant.getQuantity() < quantityToBuy) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }

            var orderItem = orderMapper.toOrderItem(itemRequest);
            orderItem.setOrder(order);
            orderItem.setProduct(variant.getProduct());
            orderItem.setProductVariant(variant);
            orderItemList.add(orderItem);
        }

        order.setOrderItems(orderItemList);
        order.setUser(user);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        order.setVoucher(voucher);
        order.setOrderCode(generateOrderCode());
        calculateAmount(order);
        try {

            Coordinates coordinates =
                    geocodingService.getCoordinates(request.getWard(), request.getDistrict(), request.getProvince());

            if (coordinates != null) {
                order.setDeliveryLatitude(coordinates.getLatitude());
                order.setDeliveryLongitude(coordinates.getLongitude());
            }
        } catch (Exception e) {
            throw e;
        }
        Payment payment = paymentMapper.toPayment(request.getPayment());

        payment.setAmount(order.getFinalAmount());
        payment.setOrder(order);
        order.setPayment(payment);

        try {
            order = orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            log.error("Error in create order: ", e);
            throw new AppException(ErrorCode.ORDER_CREATE_FAIL);
        }

        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    public OrderResponse createOrderForAdmin(OrderCreationRequest request) {

        Voucher voucher =
                voucherRepository.findByVoucherCode(request.getVoucherCode()).orElse(null);

        Set<Long> variantIds = request.getOrderItems().stream()
                .map(OrderItemRequest::getProductVariantId)
                .collect(Collectors.toSet());

        Map<Long, ProductVariant> productVariantMap = variantRepository.findAllById(variantIds).stream()
                .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

        List<OrderItem> orderItemList = new ArrayList<>();

        Order order = orderMapper.toOrder(request);

        for (var itemRequest : request.getOrderItems()) {
            var variant = productVariantMap.get(itemRequest.getProductVariantId());
            int quantityToBuy = itemRequest.getQuantity();

            if (variant == null) {
                throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED);
            }

            if (variant.getQuantity() < quantityToBuy) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }

            var orderItem = orderMapper.toOrderItem(itemRequest);
            orderItem.setOrder(order);
            orderItem.setProduct(variant.getProduct());
            orderItem.setProductVariant(variant);
            orderItemList.add(orderItem);
        }

        order.setOrderItems(orderItemList);
        order.setStatus(OrderStatus.PENDING);
        order.setVoucher(voucher);
        order.setOrderCode(generateOrderCode());
        calculateAmount(order);
        try {

            Coordinates coordinates =
                    geocodingService.getCoordinates(request.getWard(), request.getDistrict(), request.getProvince());

            if (coordinates != null) {
                order.setDeliveryLatitude(coordinates.getLatitude());
                order.setDeliveryLongitude(coordinates.getLongitude());
            }
        } catch (Exception e) {
            throw e;
        }
        Payment payment = paymentMapper.toPayment(request.getPayment());

        payment.setAmount(order.getFinalAmount());
        payment.setOrder(order);
        order.setPayment(payment);

        try {
            order = orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            log.error("Error in create order: ", e);
            throw new AppException(ErrorCode.ORDER_CREATE_FAIL);
        }

        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    public OrderResponse confirmOrder(long id) {

        var order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIST));
        User user = userService.getCurrentUser();

        if (!order.getStatus().name().equals(OrderStatus.PENDING.name())) {
            throw new AppException(ErrorCode.ORDER_CONFIRM_STATUS_INVALID);
        }

        if (order.getDeliveryLatitude() == null || order.getDeliveryLongitude() == null) {
            throw new AppException(ErrorCode.ORDER_COORDINATES_MISSING);
        }

        List<Warehouse> sortedWarehouses =
                warehouseRepository.findAllSortedByDistance(order.getDeliveryLatitude(), order.getDeliveryLongitude());
        log.info("Sort Warehouse is: {}", sortedWarehouses);
        if (sortedWarehouses.isEmpty()) {
            throw new AppException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }

        Warehouse assignedWarehouse = null;
        Map<Long, Inventory> fulfillmentInventoryMap = null;
        Set<Long> variantIds = order.getOrderItems().stream()
                .map((item) -> item.getProductVariant().getId())
                .collect(Collectors.toSet());
        for (var wh : sortedWarehouses) {
            Map<Long, Inventory> currentInventoryMap =
                    inventoryRepository.findByWarehouseIdAndProductVariant_IdIn(wh.getId(), variantIds).stream()
                            .collect(Collectors.toMap(
                                    (inventory) -> inventory.getProductVariant().getId(), Function.identity()));

            boolean canFulfill = true;
            for (var item : order.getOrderItems()) {
                var inventory = currentInventoryMap.get(item.getProductVariant().getId());
                if (inventory == null || inventory.getQuantity() < item.getQuantity()) {
                    canFulfill = false;
                    break; // kho ko phù hợp, next
                }
            }

            if (canFulfill) {
                assignedWarehouse = wh;
                fulfillmentInventoryMap = currentInventoryMap;
                break; // thoát vòng lặp sau khi tìm thấy kho phù hợp
            }
        }

        // null tức là ko có kho phù hợp
        if (assignedWarehouse != null) {
            log.info("Warehouse is: {}", assignedWarehouse);
            Map<Long, ProductVariant> productVariantMap = variantRepository.findAllById(variantIds).stream()
                    .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

            List<Inventory> inventoriesToUpdate = new ArrayList<>();
            List<ProductVariant> variantToUpdate = new ArrayList<>();
            List<InventoryTransaction> inventoryTransactionsToCreate = new ArrayList<>();
            for (var item : order.getOrderItems()) {
                Long variantId = item.getProductVariant().getId();
                int quantityToBuy = item.getQuantity();

                var variant = productVariantMap.get(variantId);

                Inventory inventory = fulfillmentInventoryMap.get(variantId);

                if (inventory.getQuantity() < quantityToBuy) {
                    throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
                }
                // trừ kho đơn lẻ
                inventory.setQuantity(inventory.getQuantity() - quantityToBuy);
                inventoriesToUpdate.add(inventory);

                // trừ tổng kho
                variant.setQuantity(variant.getQuantity() - quantityToBuy);
                variantToUpdate.add(variant);

                // ghi ls giao dịch kho
                InventoryTransaction inventoryTransaction = InventoryTransaction.builder()
                        .warehouse(assignedWarehouse)
                        .productVariant(variant)
                        .createBy(user)
                        .quantityChange(-item.getQuantity())
                        .type(InventoryReferenceType.EXPORT_TO_CUSTOMER)
                        .note("Exports to Order" + order.getOrderCode())
                        .referenceId(order.getId())
                        .build();
                inventoryTransactionsToCreate.add(inventoryTransaction);
            }

            order.setStatus(OrderStatus.CONFIRMED);
            order.setWarehouse(assignedWarehouse);
            OrderStatusHistory history = OrderStatusHistory.builder()
                    .order(order)
                    .oldStatus(OrderStatus.PENDING.name())
                    .newStatus(OrderStatus.CONFIRMED.name())
                    .changeBy(user)
                    .build();

            try {
                order = orderRepository.save(order);
                inventoryRepository.saveAll(inventoriesToUpdate);
                variantRepository.saveAll(variantToUpdate);
                orderStatusHistoryRepository.save(history);
                inventoryTransactionRepository.saveAll(inventoryTransactionsToCreate);
            } catch (DataIntegrityViolationException e) {
                log.error("Error in confirm order: ", e);
                throw new AppException(ErrorCode.ORDER_CONFIRM_FAIL);
            } catch (ObjectOptimisticLockingFailureException exception) {
                throw new AppException(ErrorCode.PRODUCT_JUST_SOLD_OUT);
            }

            return orderMapper.toOrderResponse(order);
        }
        // ko có kho phù hợp -> ném lỗi
        throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
    }

    @Transactional
    public OrderResponse cancelOrder(long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIST));
        User user = userService.getCurrentUser();

        if (!order.getStatus().name().equals(OrderStatus.PENDING.name())) {
            throw new AppException(ErrorCode.ORDER_CANCEL_STATUS_INVALID);
        }

        order.setStatus(OrderStatus.CANCELLED);
        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .oldStatus(order.getStatus().name())
                .newStatus(OrderStatus.CANCELLED.name())
                .changeBy(user)
                .build();

        order = orderRepository.save(order);

        orderStatusHistoryRepository.save(history);

        return orderMapper.toOrderResponse(order);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<SimpleOrderResponse> getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var order = orderRepository.findAll(pageable);

        return order.map(orderMapper::toSimpleOrderResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public OrderResponse getOrderById(long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIST));
        return orderMapper.toOrderResponse(order);
    }

    public OrderResponseForCustomer getOrderByIdForCustomer(long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIST));

        return orderMapper.toOrderResponseForCustomer(order);
    }

    public Page<SimpleOrderResponseForCustomer> getMyOrders(int page, int size, String sortBy) {

        User user = userService.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        var order = orderRepository.findAllByUserId(user.getId(), pageable);
        return order.map(orderMapper::toSimpleOrderResponseForCustomer);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<SimpleOrderResponse> searchOrders(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository
                .findAll(OrderSpecification.createSpecification(keyword), pageable)
                .map(orderMapper::toSimpleOrderResponse);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public OrderResponse updateStatus(long id, OrderUpdateStatusRequest request) {
        var order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIST));

        User user = userService.getCurrentUser();

        if (order.getStatus().name().equals(OrderStatus.CANCELLED.name())) {
            throw new AppException(ErrorCode.ORDER_ALREADY_CANCELED);
        }

        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .oldStatus(order.getStatus().name())
                .newStatus(request.getOrderStatus().name())
                .changeBy(user)
                .build();

        order.setStatus(request.getOrderStatus());
        order.setUpdateBy(user);

        List<Inventory> inventoriesToUpdate = new ArrayList<>();
        List<ProductVariant> variantToUpdate = new ArrayList<>();
        List<InventoryTransaction> inventoryTransactionsToCreate = new ArrayList<>();
        if (request.getOrderStatus().equals(OrderStatus.CANCELLED)) {
            Set<Long> variantIds = order.getOrderItems().stream()
                    .map((item) -> item.getProductVariant().getId())
                    .collect(Collectors.toSet());

            Map<Long, ProductVariant> productVariantMap = variantRepository.findAllById(variantIds).stream()
                    .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

            Map<Long, Inventory> inventoryMap =
                    inventoryRepository
                            .findByWarehouseIdAndProductVariant_IdIn(
                                    order.getWarehouse().getId(), variantIds)
                            .stream()
                            .collect(Collectors.toMap(
                                    (inventory) -> inventory.getProductVariant().getId(), Function.identity()));

            for (var item : order.getOrderItems()) {
                Long variantId = item.getProductVariant().getId();
                int quantityToBuy = item.getQuantity();

                var variant = productVariantMap.get(variantId);

                Inventory inventory = inventoryMap.get(variantId);

                // trả lại kho đơn lẻ
                inventory.setQuantity(inventory.getQuantity() + quantityToBuy);
                inventoriesToUpdate.add(inventory);

                // trả lại tổng kho
                variant.setQuantity(variant.getQuantity() + quantityToBuy);
                variantToUpdate.add(variant);

                // ghi ls giao dịch kho
                InventoryTransaction inventoryTransaction = InventoryTransaction.builder()
                        .warehouse(order.getWarehouse())
                        .productVariant(variant)
                        .createBy(user)
                        .quantityChange(+item.getQuantity())
                        .type(InventoryReferenceType.CANCEL_ORDER_RETURN)
                        .note("Return by cancel Order" + order.getOrderCode())
                        .referenceId(order.getId())
                        .build();
                inventoryTransactionsToCreate.add(inventoryTransaction);
            }
        }
        if (request.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
            Set<Long> variantIds = order.getOrderItems().stream()
                    .map((item) -> item.getProductVariant().getId())
                    .collect(Collectors.toSet());

            Map<Long, ProductVariant> productVariantMap = variantRepository.findAllById(variantIds).stream()
                    .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

            Map<Long, Inventory> inventoryMap =
                    inventoryRepository
                            .findByWarehouseIdAndProductVariant_IdIn(
                                    order.getWarehouse().getId(), variantIds)
                            .stream()
                            .collect(Collectors.toMap(
                                    (inventory) -> inventory.getProductVariant().getId(), Function.identity()));

            for (var item : order.getOrderItems()) {
                Long variantId = item.getProductVariant().getId();
                int quantityToBuy = item.getQuantity();

                var variant = productVariantMap.get(variantId);

                Inventory inventory = inventoryMap.get(variantId);

                // trừ kho đơn lẻ
                inventory.setQuantity(inventory.getQuantity() - quantityToBuy);
                inventoriesToUpdate.add(inventory);

                // trừ tổng kho
                variant.setQuantity(variant.getQuantity() - quantityToBuy);
                variantToUpdate.add(variant);

                // ghi ls giao dịch kho
                InventoryTransaction inventoryTransaction = InventoryTransaction.builder()
                        .warehouse(order.getWarehouse())
                        .productVariant(variant)
                        .createBy(user)
                        .quantityChange(+item.getQuantity())
                        .type(InventoryReferenceType.EXPORT_TO_CUSTOMER)
                        .note("Exports to Order" + order.getOrderCode())
                        .referenceId(order.getId())
                        .build();
                inventoryTransactionsToCreate.add(inventoryTransaction);
            }
        }

        try {
            order = orderRepository.save(order);
            inventoryRepository.saveAll(inventoriesToUpdate);
            variantRepository.saveAll(variantToUpdate);
            orderStatusHistoryRepository.save(history);
            inventoryTransactionRepository.saveAll(inventoryTransactionsToCreate);
        } catch (DataIntegrityViolationException e) {
            log.error("Error in update order status: ", e);
            throw new AppException(ErrorCode.ORDER_CREATE_FAIL);
        }
        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public OrderResponse update(long id, OrderUpdateRequest request) {

        var order = orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIST));

        if (!order.getStatus().name().equals(OrderStatus.PENDING.name())) {
            throw new AppException(ErrorCode.ORDER_CHANGE_INFO_STATUS_INVALID);
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        orderMapper.updateOrder(order, request);
        order.setUpdateBy(user);

        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            log.error("Error when update order: ", e);
            throw new RuntimeException(e);
        }

        return orderMapper.toOrderResponse(order);
    }

    String generateOrderCode() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORD" + datePart + "-" + randomPart;
    }

    void calculateAmount(Order order) {
        double totalAmount = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        order.setTotalAmount(totalAmount);

        if (order.getVoucher() == null) {
            order.setReducedAmount(0);
            order.setFinalAmount(totalAmount);
        } else {
            Voucher voucher = order.getVoucher();
            double reducedAmount = 0;

            boolean isActive = voucher.getStatus() == VoucherStatus.ACTIVE;
            boolean isExpire = LocalDateTime.now().isAfter(voucher.getStartDate())
                    && LocalDateTime.now().isBefore(voucher.getEndDate());

            if (!isExpire || !isActive || totalAmount < voucher.getMinApplicablePrice()) {
                order.setReducedAmount(0);
                order.setFinalAmount(totalAmount);
                return;
            }

            switch (voucher.getType()) {
                case PERCENTAGE -> reducedAmount = totalAmount * voucher.getDiscountValue() / 100;
                case FIXED_AMOUNT -> reducedAmount = voucher.getDiscountValue();
            }

            if (voucher.getMaxDiscountAmount() > 0) {
                reducedAmount = Math.min(voucher.getMaxDiscountAmount(), reducedAmount);
            }

            reducedAmount = Math.min(reducedAmount, totalAmount);

            order.setReducedAmount(reducedAmount);
            order.setFinalAmount(totalAmount - reducedAmount);
        }
    }
}
