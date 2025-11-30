package com.nguyenkhang.mobile_store.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.nguyenkhang.mobile_store.dto.request.products.ProductSearchCriteria;
import com.nguyenkhang.mobile_store.dto.response.product.*;
import com.nguyenkhang.mobile_store.repository.*;
import com.nguyenkhang.mobile_store.repository.ProductDocumentRepository;
import com.nguyenkhang.mobile_store.specification.ProductSpecification;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nguyenkhang.mobile_store.dto.request.products.ProductAndVariantsCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.products.ProductRequest;
import com.nguyenkhang.mobile_store.dto.response.UploadFileCloudinaryResponse;
import com.nguyenkhang.mobile_store.entity.*;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.ProductMapper;
import com.nguyenkhang.mobile_store.mapper.ProductVariantMapper;
import com.nguyenkhang.mobile_store.utils.VariantUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;
    ProductVariantRepository productVariantRepository;
    OptionValueRepository optionValueRepository;
    ProductDocumentRepository productDocumentRepository;

    ProductVariantMapper variantMapper;
    CategoryRepository categoryRepository;
    BrandRepository brandRepository;
    CloudinaryService cloudinaryService;
    UserService userService;

    String CLOUDINARY_FOLDER = "products";
    String VARIANT_IMAGE_FOLDER = CLOUDINARY_FOLDER + "/variants";

    EntityManager entityManager;


    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ProductResponse createProductAndVariants(ProductAndVariantsCreationRequest request) {
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Brand brand = brandRepository
                .findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        User userCreate = userService.getCurrentUser();

        var product = productMapper.toProduct(request);
        product.setCategory(category);
        product.setBrand(brand);
        product.setCreateBy(userCreate);

        UploadFileCloudinaryResponse mainImageLink = cloudinaryService.safeUpload(
                request.getMainImageFile(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

        product.setMainImageUrl(mainImageLink.getPublicUrl());
        product.setImagePublicId(mainImageLink.getPublicId());

        Set<Long> optionValueIds = request.getVariants().stream()
                .flatMap((variant) -> variant.getOptionValues().stream())
                .collect(Collectors.toSet());

        Map<Long, OptionValue> optionValueMap = optionValueRepository.findAllById(optionValueIds).stream()
                .collect(Collectors.toMap(OptionValue::getId, Function.identity()));

        List<ProductVariant> variants = new ArrayList<>();

        for (var variantRequest : request.getVariants()) {
            Set<OptionValue> optionValues = variantRequest.getOptionValues().stream()
                    .map(optionValueMap::get)
                    .collect(Collectors.toSet());

            if (optionValues.size() != variantRequest.getOptionValues().size()) {
                throw new AppException(ErrorCode.OPTION_VALUE_NOT_EXISTED);
            }
            ProductVariant productVariant = variantMapper.toVariantCreateMany(variantRequest);
            productVariant.setProduct(product);
            productVariant.setOptionValues(optionValues);
            productVariant.setCreateBy(userCreate);
            productVariant.setSignature(VariantUtils.createVariantSignature(optionValues));

            UploadFileCloudinaryResponse imageLink = cloudinaryService.safeUpload(
                    variantRequest.getImageFile(), VARIANT_IMAGE_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ProductResponse getById(long id) {

        Product product =
                productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponse(product);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ProductResponse update(long productId, ProductRequest request) {
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Brand brand = brandRepository
                .findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        User userUpdate = userService.getCurrentUser();

        productMapper.updateProduct(product, request);
        product.setBrand(brand);
        product.setCategory(category);
        product.setUpdateBy(userUpdate);

        if (request.getMainImageFile() != null && !request.getMainImageFile().isEmpty()) {

            cloudinaryService.safeDelete(product.getImagePublicId(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);

            UploadFileCloudinaryResponse mainImageLink = cloudinaryService.safeUpload(
                    request.getMainImageFile(), CLOUDINARY_FOLDER, ErrorCode.IMAGE_UPLOAD_FAIL);

            product.setMainImageUrl(mainImageLink.getPublicUrl());
            product.setImagePublicId(mainImageLink.getPublicId());
        }

        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    public Page<SimpleProductResponseForCustomer> getProductsByCategory(
            long categoryId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var products = productRepository.findAllByCategoryId(categoryId, pageable);

        return products.map(productMapper::toSimpleProductResponseForCustomer);
    }

    public Page<SimpleProductResponseForCustomer> getProductsByBrand(long brandId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var products = productRepository.findAllByBrandId(brandId, pageable);

        return products.map(productMapper::toSimpleProductResponseForCustomer);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
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

        Product product =
                productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponseForCustomer(product);
    }

    @Transactional(rollbackFor = ConstraintViolationException.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        var mainImageId = product.getImagePublicId();
        List<String> variantImageIds = product.getProductVariants().stream()
                .map(ProductVariant::getImagePublicId)
                .toList();

        try{
            productRepository.delete(product);
            entityManager.flush();
        }catch (ConstraintViolationException exception){
            throw new AppException(ErrorCode.PRODUCT_CAN_NOT_DELETE);
        }

        for (var id : variantImageIds) {
            cloudinaryService.safeDelete(id, VARIANT_IMAGE_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);
        }

        cloudinaryService.safeDelete(mainImageId, CLOUDINARY_FOLDER, ErrorCode.IMAGE_DELETE_FAIL);
    }

    public Page<SimpleProductSearchResponse> searchProducts(String keyword, int page){
        Pageable pageable = PageRequest.of(page, 20);

        var productDocuments = productDocumentRepository.searchByMultiMatch(keyword, pageable);


        return productDocuments.map(productMapper::toSimpleProductSearchResponse);
    }

    public Page<SimpleProductResponse> searchProductsForAdmin(ProductSearchCriteria criteria, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        var spec = ProductSpecification.withCriteria(criteria);

        var products = productRepository.findAll(spec,pageable);


        return products.map(productMapper::toSimpleProductResponse);
    }

    ProductDocument mapToDocument(Product product){
        Double minPrice = null;
        Double maxPrice = null;

        var variants = product.getProductVariants();

        if (variants != null && !variants.isEmpty()){
            minPrice = product.getProductVariants().stream().mapToDouble(ProductVariant::getPrice).min().getAsDouble();
            maxPrice = product.getProductVariants().stream().mapToDouble(ProductVariant::getPrice).max().getAsDouble();
        }

        List<String> attributes = variants.stream()
                .flatMap(productVariant -> productVariant.getOptionValues()
                .stream()).map(OptionValue::getValue)
                .distinct()
                .toList();

        return ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .mainImageUrl(product.getMainImageUrl())
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .variantAttributes(attributes)
                .build();
    }


    public void syncProductToElasticSearch(Product product){
        ProductDocument document = mapToDocument(product);
        productDocumentRepository.save(document);
    }

    public void removeProductFromElastic (Long productId){
        productDocumentRepository.deleteById(productId);
    }
}
