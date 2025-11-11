package com.nguyenkhang.mobile_store.dto.response.order;

import com.nguyenkhang.mobile_store.dto.response.product.ProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponse;
import com.nguyenkhang.mobile_store.dto.response.product_variant.ProductVariantResponse;
import com.nguyenkhang.mobile_store.dto.response.product_variant.SimpleProductVariantResponse;
import com.nguyenkhang.mobile_store.entity.Order;
import com.nguyenkhang.mobile_store.entity.Product;
import com.nguyenkhang.mobile_store.entity.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {

    Long id;

    SimpleProductResponse product;

    SimpleProductVariantResponse productVariant;

    int quantity;

    double pricePerUnit;

    double totalPrice;
}
