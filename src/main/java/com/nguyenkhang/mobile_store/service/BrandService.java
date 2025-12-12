package com.nguyenkhang.mobile_store.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.nguyenkhang.mobile_store.dto.request.brand.BrandRequest;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandService {

    BrandMapper brandMapper;
    BrandRepository brandRepository;
    EntityManager entityManager;

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

    @Transactional(rollbackFor = ConstraintViolationException.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));

        try {
            brandRepository.delete(brand);
            entityManager.flush();
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CANNOT_DELETE_CATEGORY);
        }
    }
}
