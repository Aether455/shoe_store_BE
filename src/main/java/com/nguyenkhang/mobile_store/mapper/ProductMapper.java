package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.products.ProductAndVariantsCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.products.ProductRequest;
import com.nguyenkhang.mobile_store.dto.response.product.ProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product.ProductResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponseForCustomer;
import com.nguyenkhang.mobile_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    Product toProduct(ProductAndVariantsCreationRequest request);

    ProductResponse toProductResponse(Product product);
    SimpleProductResponse toSimpleProductResponse(Product product);

    ProductResponseForCustomer toProductResponseForCustomer(Product product);
    SimpleProductResponseForCustomer toSimpleProductResponseForCustomer(Product product);


    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductRequest request);
}
