package com.nguyenkhang.shoe_store.service;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.shoe_store.dto.request.brand.BrandRequest;
import com.nguyenkhang.shoe_store.dto.response.brand.BrandResponse;
import com.nguyenkhang.shoe_store.dto.response.brand.BrandResponseForCustomer;
import com.nguyenkhang.shoe_store.entity.Brand;
import com.nguyenkhang.shoe_store.exception.AppException;
import com.nguyenkhang.shoe_store.exception.ErrorCode;
import com.nguyenkhang.shoe_store.mapper.BrandMapper;
import com.nguyenkhang.shoe_store.repository.BrandRepository;

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

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));

        try {
            brandRepository.delete(brand);
            brandRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CANNOT_DELETE_BRAND);
        }
    }
}
