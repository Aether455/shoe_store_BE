package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.products.ProductAndVariantsCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.products.ProductRequest;
import com.nguyenkhang.mobile_store.dto.response.UploadFileCloudinaryResponse;
import com.nguyenkhang.mobile_store.dto.response.product.ProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product.ProductResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.*;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.ProductMapper;
import com.nguyenkhang.mobile_store.mapper.ProductVariantMapper;
import com.nguyenkhang.mobile_store.repository.*;
import com.nguyenkhang.mobile_store.utils.VariantUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;
    ProductVariantRepository productVariantRepository;
    OptionValueRepository optionValueRepository;

    ProductVariantMapper variantMapper;
    CategoryRepository categoryRepository;
    BrandRepository brandRepository;
    CloudinaryService cloudinaryService;
    UserService userService;

    String CLOUDINARY_FOLDER = "products";
    String VARIANT_IMAGE_FOLDER = CLOUDINARY_FOLDER + "/variants";

    @Transactional
    public ProductResponse createProductAndVariants(ProductAndVariantsCreationRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        User userCreate = userService.getCurrentUser();

        var product = productMapper.toProduct(request);
        product.setCategory(category);
        product.setBrand(brand);
        product.setCreateBy(userCreate);

        UploadFileCloudinaryResponse mainImageLink = cloudinaryService.safeUpload(request.getMainImageFile(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

        product.setMainImageUrl(mainImageLink.getPublicUrl());
        product.setImagePublicId(mainImageLink.getPublicId());


        Set<Long> optionValueIds = request.getVariants().stream().flatMap(
                (variant) -> variant.getOptionValues().stream()
        ).collect(Collectors.toSet());

        Map<Long,OptionValue> optionValueMap = optionValueRepository.findAllById(optionValueIds).stream().collect(Collectors.toMap(OptionValue::getId, Function.identity()));

        List<ProductVariant> variants = new ArrayList<>();

        for (var variantRequest : request.getVariants()) {
            Set<OptionValue> optionValues = variantRequest.getOptionValues().stream().map(optionValueMap::get).collect(Collectors.toSet());

            if (optionValues.size() != variantRequest.getOptionValues().size()) {
                throw new AppException(ErrorCode.OPTION_VALUE_NOT_EXISTED);
            }
            ProductVariant productVariant = variantMapper.toVariantCreateMany(variantRequest);
            productVariant.setProduct(product);
            productVariant.setOptionValues(optionValues);
            productVariant.setCreateBy(userCreate);
            productVariant.setSignature(VariantUtils.createVariantSignature(optionValues));

            UploadFileCloudinaryResponse imageLink = cloudinaryService.safeUpload(variantRequest.getImageFile(), VARIANT_IMAGE_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

            productVariant.setProductVariantImageUrl(imageLink.getPublicUrl());
            productVariant.setImagePublicId(imageLink.getPublicId());
            variants.add(productVariant);
        }
        try {
            product.setProductVariants(variants);
            productVariantRepository.saveAll(variants);
        } catch (DataIntegrityViolationException e) {
            log.error("Error in create variant: {}", e.toString());
            throw new AppException(ErrorCode.PRODUCT_VARIANT_EXISTED);
        }
        product = productRepository.save(product);



        return productMapper.toProductResponse(product);
    }

    public ProductResponse getById(long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponse(product);
    }

    @Transactional
    public ProductResponse update(long productId, ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        User userUpdate = userService.getCurrentUser();

        productMapper.updateProduct(product, request);
        product.setBrand(brand);
        product.setCategory(category);
        product.setUpdateBy(userUpdate);


        if ( request.getMainImageFile()!=null && !request.getMainImageFile().isEmpty()) {

            cloudinaryService.safeDelete(product.getImagePublicId(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);

            UploadFileCloudinaryResponse mainImageLink = cloudinaryService.safeUpload(request.getMainImageFile(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

            product.setMainImageUrl(mainImageLink.getPublicUrl());
            product.setImagePublicId(mainImageLink.getPublicId());
        }

        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    public Page<SimpleProductResponseForCustomer> getProductsByCategory(long categoryId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var products = productRepository.findAllByCategoryId(categoryId, pageable);

        return products.map(productMapper::toSimpleProductResponseForCustomer);
    }

    public Page<SimpleProductResponseForCustomer> getProductsByBrand(long brandId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var products = productRepository.findAllByBrandId(brandId, pageable);

        return products.map(productMapper::toSimpleProductResponseForCustomer);
    }


    public Page<SimpleProductResponse> getProducts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var products = productRepository.findAll(pageable);

        return products.map(productMapper::toSimpleProductResponse);
    }

    public Page<SimpleProductResponseForCustomer> getProductsForUser(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var products = productRepository.findAll(pageable);

        return products.map(productMapper::toSimpleProductResponseForCustomer);
    }

    public ProductResponseForCustomer getByIdForUser(long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponseForCustomer(product);
    }

    @Transactional
    public void delete(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        var mainImageId = product.getImagePublicId();
        List<String> variantImageIds = product.getProductVariants().stream().map(ProductVariant::getImagePublicId).toList();

        productRepository.delete(product);

        for (var id : variantImageIds) {
            cloudinaryService.safeDelete(id, VARIANT_IMAGE_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);
        }
        cloudinaryService.safeDelete(mainImageId, CLOUDINARY_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);
    }
}
