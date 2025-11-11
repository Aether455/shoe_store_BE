package com.nguyenkhang.mobile_store.controller.user;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.products.ProductAndVariantsCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.products.ProductRequest;
import com.nguyenkhang.mobile_store.dto.request.products.ProductVariantUpdateRequest;
import com.nguyenkhang.mobile_store.dto.request.products.VariantCreationOneRequest;
import com.nguyenkhang.mobile_store.dto.response.product.ProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product.ProductResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.product_variant.ProductVariantResponse;
import com.nguyenkhang.mobile_store.service.ProductService;
import com.nguyenkhang.mobile_store.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProductController {
    ProductService productService;
    ProductVariantService variantService;

    @GetMapping
    public ApiResponse<Page<SimpleProductResponseForCustomer>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<SimpleProductResponseForCustomer>>builder().result(productService.getProductsForUser(page, size, sortBy)).build();
    }
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponseForCustomer> getProductById(@PathVariable long productId) {
        return ApiResponse.<ProductResponseForCustomer>builder().result(productService.getByIdForUser(productId)).build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<SimpleProductResponseForCustomer>> getProductsByCategory(@PathVariable long categoryId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<SimpleProductResponseForCustomer>>builder().result(productService.getProductsByCategory(categoryId, page, size, sortBy)).build();
    }

    @GetMapping("/brand/{brandId}")
    public ApiResponse<Page<SimpleProductResponseForCustomer>> getProductsByBrand(@PathVariable long brandId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<SimpleProductResponseForCustomer>>builder().result(productService.getProductsByBrand(brandId, page, size, sortBy)).build();
    }

}
