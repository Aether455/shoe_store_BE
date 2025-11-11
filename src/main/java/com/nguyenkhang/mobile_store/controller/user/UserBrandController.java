package com.nguyenkhang.mobile_store.controller.user;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.BrandRequest;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponse;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponseForCustomer;
import com.nguyenkhang.mobile_store.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserBrandController {

    BrandService brandService;


    @GetMapping
    public ApiResponse<List<BrandResponseForCustomer>> getBrands(){
        return ApiResponse.<List<BrandResponseForCustomer>>builder().result(brandService.getBrandsForUser()).build();
    }

}
