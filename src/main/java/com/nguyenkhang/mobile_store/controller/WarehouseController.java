package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.WarehouseRequest;
import com.nguyenkhang.mobile_store.dto.response.WarehouseResponse;
import com.nguyenkhang.mobile_store.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseController {
    WarehouseService warehouseService;

    @GetMapping
    public ApiResponse<Page<WarehouseResponse>> get(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sortBy){
        return ApiResponse.<Page<WarehouseResponse>>builder().result(warehouseService.get(page, size, sortBy)).build();
    }

    @PostMapping
    public ApiResponse<WarehouseResponse> create(@RequestBody @Valid WarehouseRequest request){
        return ApiResponse.<WarehouseResponse>builder().result(warehouseService.create(request)).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<WarehouseResponse> getById(@PathVariable long id){
        return ApiResponse.<WarehouseResponse>builder().result(warehouseService.getById(id)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<WarehouseResponse> update(@RequestBody @Valid WarehouseRequest request,@PathVariable long id){
        return ApiResponse.<WarehouseResponse>builder().result(warehouseService.update(id,request)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable long id){
        warehouseService.delete(id);

        return ApiResponse.<String>builder().result("Warehouse has been deleted").build();
    }
}
