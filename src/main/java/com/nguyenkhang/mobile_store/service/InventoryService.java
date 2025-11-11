package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.response.InventoryResponse;
import com.nguyenkhang.mobile_store.dto.response.WarehouseResponse;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.InventoryMapper;
import com.nguyenkhang.mobile_store.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryService {
    InventoryRepository inventoryRepository;
    InventoryMapper inventoryMapper;

    public Page<InventoryResponse> get(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var inventories = inventoryRepository.findAll(pageable);

        return inventories.map(inventoryMapper::toInventoryResponse);
    }

    public InventoryResponse getById(long id) {
        var inventory = inventoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTED));
        return inventoryMapper.toInventoryResponse(inventory);
    }
}
