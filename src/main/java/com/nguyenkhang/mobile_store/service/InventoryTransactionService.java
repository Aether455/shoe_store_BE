package com.nguyenkhang.mobile_store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.nguyenkhang.mobile_store.dto.response.InventoryTransactionResponse;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.InventoryTransactionMapper;
import com.nguyenkhang.mobile_store.repository.InventoryTransactionRepository;
import com.nguyenkhang.mobile_store.specification.InventoryTransactionSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryTransactionService {
    InventoryTransactionRepository inventoryTransactionRepository;
    InventoryTransactionMapper inventoryTransactionMapper;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<InventoryTransactionResponse> get(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var inventoryTransaction = inventoryTransactionRepository.findAll(pageable);

        return inventoryTransaction.map(inventoryTransactionMapper::toInventoryTransactionResponse);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public InventoryTransactionResponse getById(long id) {
        var inventoryTransaction = inventoryTransactionRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_TRANSACTION_NOT_EXISTED));
        return inventoryTransactionMapper.toInventoryTransactionResponse(inventoryTransaction);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<InventoryTransactionResponse> searchInventoryTransactions(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return inventoryTransactionRepository
                .findAll(InventoryTransactionSpecification.createSpecification(keyword), pageable)
                .map(inventoryTransactionMapper::toInventoryTransactionResponse);
    }
}
