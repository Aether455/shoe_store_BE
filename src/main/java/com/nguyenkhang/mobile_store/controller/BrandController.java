package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.BrandRequest;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponse;
import com.nguyenkhang.mobile_store.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {

    BrandService brandService;

    @PostMapping
    public ApiResponse<BrandResponse> createBrand(@RequestBody BrandRequest request){

        return ApiResponse.<BrandResponse>builder().result(brandService.createBrand(request)).message("Success!").build();
    }

    @GetMapping
    public ApiResponse<List<BrandResponse>> getBrands(){
        return ApiResponse.<List<BrandResponse>>builder().result(brandService.getBrands()).build();
    }

    @PutMapping("/{brandId}")
    public ApiResponse<BrandResponse> updateBrand(@RequestBody BrandRequest request, @PathVariable long brandId){

        return ApiResponse.<BrandResponse>builder().result(brandService.updateBrand(brandId,request)).message("Success!").build();
    }

    @DeleteMapping("/{brandId}")
    ApiResponse<String> delete(@PathVariable long brandId) {
        brandService.delete(brandId);
        return ApiResponse.<String>builder().message("Success!").result("Brand has been deleted").build();
    }
}
