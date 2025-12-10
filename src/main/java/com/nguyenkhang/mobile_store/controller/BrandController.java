package com.nguyenkhang.mobile_store.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.mobile_store.annotations.ApiCommonResponses;
import com.nguyenkhang.mobile_store.dto.ApiResponseDTO;
import com.nguyenkhang.mobile_store.dto.request.BrandRequest;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponse;
import com.nguyenkhang.mobile_store.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Brand", description = "Quản lý thương hiệu")
public class BrandController {

    BrandService brandService;

    @ApiCommonResponses
    @Operation(summary = "Thêm mới")
    @PostMapping
    public ApiResponseDTO<BrandResponse> createBrand(@RequestBody BrandRequest request) {

        return ApiResponseDTO.<BrandResponse>builder()
                .result(brandService.createBrand(request))
                .build();
    }

    @ApiCommonResponses
    @Operation(summary = "Lấy tất cả")
    @GetMapping
    public ApiResponseDTO<List<BrandResponse>> getBrands() {
        return ApiResponseDTO.<List<BrandResponse>>builder()
                .result(brandService.getBrands())
                .build();
    }

    @ApiCommonResponses
    @Operation(summary = "Update")
    @PutMapping("/{brandId}")
    public ApiResponseDTO<BrandResponse> updateBrand(@RequestBody BrandRequest request, @PathVariable long brandId) {

        return ApiResponseDTO.<BrandResponse>builder()
                .result(brandService.updateBrand(brandId, request))
                .build();
    }

    @Operation(summary = "Xóa")
    @DeleteMapping("/{brandId}")
    ApiResponseDTO<String> delete(@PathVariable long brandId) {
        brandService.delete(brandId);
        return ApiResponseDTO.<String>builder().result("Brand has been deleted").build();
    }
}
