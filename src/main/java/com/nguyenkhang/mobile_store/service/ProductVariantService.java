package com.nguyenkhang.mobile_store.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.mobile_store.dto.request.products.ProductVariantUpdateRequest;
import com.nguyenkhang.mobile_store.dto.request.products.VariantCreationOneRequest;
import com.nguyenkhang.mobile_store.dto.response.UploadFileCloudinaryResponse;
import com.nguyenkhang.mobile_store.dto.response.product_variant.ProductVariantResponse;
import com.nguyenkhang.mobile_store.entity.OptionValue;
import com.nguyenkhang.mobile_store.entity.Product;
import com.nguyenkhang.mobile_store.entity.User;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.ProductVariantMapper;
import com.nguyenkhang.mobile_store.repository.*;
import com.nguyenkhang.mobile_store.utils.VariantUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantService {
    private static final Logger log = LoggerFactory.getLogger(ProductVariantService.class);
    ProductVariantMapper variantMapper;
    ProductVariantRepository variantRepository;
    ProductRepository productRepository;
    OptionValueRepository optionValueRepository;
    UserService userService;

    CloudinaryService cloudinaryService;
    String CLOUDINARY_FOLDER = "products/variants";




    EntityManager entityManager;

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ProductVariantResponse createOne(VariantCreationOneRequest variantRequest) {
        Product product = productRepository
                .findById(variantRequest.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        User userCreate = userService.getCurrentUser();

        var option = optionValueRepository.findAllById(variantRequest.getOptionValues());
        Set<OptionValue> optionValue = new HashSet<>(option);

        var variant = variantMapper.toVariantCreateOne(variantRequest);
        variant.setProduct(product);
        variant.setCreateBy(userCreate);
        variant.setOptionValues(optionValue);
        variant.setSignature(VariantUtils.createVariantSignature(optionValue));

        UploadFileCloudinaryResponse imageLink = cloudinaryService.safeUpload(
                variantRequest.getImageFile(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

        variant.setProductVariantImageUrl(imageLink.getPublicUrl());
        variant.setImagePublicId(imageLink.getPublicId());

        try {
            variant = variantRepository.saveAndFlush(variant);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_EXISTED);
        }

        return variantMapper.toProductVariantResponse(variant);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ProductVariantResponse update(long variantId, ProductVariantUpdateRequest variantUpdateRequest) {
        User userUpdate = userService.getCurrentUser();

        var variant = variantRepository
                .findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));

        variantMapper.updateProductVariant(variant, variantUpdateRequest);

        if (variantUpdateRequest.getOptionValues() != null
                && !variantUpdateRequest.getOptionValues().isEmpty()) {
            var option = optionValueRepository.findAllById(variantUpdateRequest.getOptionValues());
            Set<OptionValue> newOptionValue = new HashSet<>(option);

            if (!isSameOptionValue(variant.getOptionValues(), newOptionValue)) {
                variant.setOptionValues(newOptionValue);
                variant.setSignature(VariantUtils.createVariantSignature(newOptionValue));
            }
        }

        variant.setUpdateBy(userUpdate);

        if (variantUpdateRequest.getImageFile() != null
                && !variantUpdateRequest.getImageFile().isEmpty()) {

            cloudinaryService.safeDelete(variant.getImagePublicId(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);

            UploadFileCloudinaryResponse imageLink = cloudinaryService.safeUpload(
                    variantUpdateRequest.getImageFile(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

            variant.setProductVariantImageUrl(imageLink.getPublicUrl());
            variant.setImagePublicId(imageLink.getPublicId());
        }

        try {
            variant = variantRepository.save(variant);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_EXISTED);
        }
        return variantMapper.toProductVariantResponse(variant);
    }

    @Transactional(rollbackFor = ConstraintViolationException.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long id) {
        var variant = variantRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));

        String imagePublicId = variant.getImagePublicId();

        try{
            variantRepository.delete(variant);
            entityManager.flush();
        }catch (ConstraintViolationException exception){
            throw new AppException(ErrorCode.VARIANT_CAN_NOT_DELETE);
        }

        cloudinaryService.safeDelete(imagePublicId, CLOUDINARY_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);
    }

    private boolean isSameOptionValue(Set<OptionValue> optionValuesOld, Set<OptionValue> optionValuesNew) {
        if (optionValuesNew == null || optionValuesOld == null) return false;

        Set<Long> oldIds = optionValuesOld.stream().map(OptionValue::getId).collect(Collectors.toSet());
        Set<Long> newIds = optionValuesNew.stream().map(OptionValue::getId).collect(Collectors.toSet());
        return oldIds.equals(newIds);
    }
}
