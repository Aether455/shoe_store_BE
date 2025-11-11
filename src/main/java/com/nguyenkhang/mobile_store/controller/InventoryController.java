package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.response.InventoryResponse;
import com.nguyenkhang.mobile_store.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {
    InventoryService inventoryService;

    @GetMapping
    public ApiResponse<Page<InventoryResponse>> get(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sortBy){
        return ApiResponse.<Page<InventoryResponse>>builder().result(inventoryService.get(page, size, sortBy)).build();
    }
    @GetMapping("/{id}")
    public ApiResponse<InventoryResponse> getById(@PathVariable long id){
        return ApiResponse.<InventoryResponse>builder().result(inventoryService.getById(id)).build();
    }



}
