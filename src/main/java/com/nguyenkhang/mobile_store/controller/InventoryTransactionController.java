package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.response.InventoryResponse;
import com.nguyenkhang.mobile_store.dto.response.InventoryTransactionResponse;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.service.InventoryService;
import com.nguyenkhang.mobile_store.service.InventoryTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory-transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryTransactionController {
    InventoryTransactionService transactionService;

    @GetMapping
    public ApiResponse<Page<InventoryTransactionResponse>> getTransactions(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "id") String sortBy){
        return ApiResponse.<Page<InventoryTransactionResponse>>builder().result(transactionService.get(page, size, sortBy)).build();
    }



    @GetMapping("/{id}")
    public ApiResponse<InventoryTransactionResponse> getTransactionById(@PathVariable long id){
        return ApiResponse.<InventoryTransactionResponse>builder().result(transactionService.getById(id)).build();
    }


    @GetMapping("/search")
    public ApiResponse<Page<InventoryTransactionResponse>> searchInventoryTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String keyword){
        return ApiResponse.<Page<InventoryTransactionResponse>>builder().result(transactionService.searchInventoryTransactions(keyword, page)).build();
    }
}
