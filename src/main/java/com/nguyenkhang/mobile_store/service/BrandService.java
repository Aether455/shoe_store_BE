package com.nguyenkhang.mobile_store.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.nguyenkhang.mobile_store.dto.request.BrandRequest;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponse;
import com.nguyenkhang.mobile_store.dto.response.brand.BrandResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.Brand;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.BrandMapper;
import com.nguyenkhang.mobile_store.repository.BrandRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandService {

    BrandMapper brandMapper;
    BrandRepository brandRepository;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public BrandResponse createBrand(BrandRequest request) {
        Brand brand = brandMapper.toBrand(request);

        brand = brandRepository.save(brand);

        return brandMapper.toBrandResponse(brand);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public List<BrandResponse> getBrands() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toBrandResponse)
                .toList();
    }

    public List<BrandResponseForCustomer> getBrandsForUser() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toBrandResponseForCustomer)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public BrandResponse updateBrand(long id, BrandRequest request) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));

        brandMapper.updateBrand(brand, request);

        brand = brandRepository.save(brand);

        return brandMapper.toBrandResponse(brand);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        brandRepository.deleteById(id);
    }
}
