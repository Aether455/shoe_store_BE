package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.SupplierRequest;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.service.SupplierService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierController {
    SupplierService supplierService;

    @GetMapping
    public ApiResponse<Page<SupplierResponse>> get(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "id") String sortBy){
        return ApiResponse.<Page<SupplierResponse>>builder().result(supplierService.get(page, size, sortBy)).build();
    }

    @PostMapping
    public ApiResponse<SupplierResponse> create(@RequestBody @Valid SupplierRequest request){
        return ApiResponse.<SupplierResponse>builder().result(supplierService.create(request)).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SupplierResponse> getById(@PathVariable long id){
        return ApiResponse.<SupplierResponse>builder().result(supplierService.getById(id)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SupplierResponse> update(@RequestBody @Valid SupplierRequest request,@PathVariable long id){
        return ApiResponse.<SupplierResponse>builder().result(supplierService.update(id,request)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable long id){
        supplierService.delete(id);

        return ApiResponse.<String>builder().result("Supplier has been deleted").build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<SupplierResponse>> searchSupplier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String keyword){
        return ApiResponse.<Page<SupplierResponse>>builder().result(supplierService.searchSupplier(keyword, page)).build();
    }
}
