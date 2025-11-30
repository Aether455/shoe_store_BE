package com.nguyenkhang.mobile_store.dto.response.purchase_order;

import com.nguyenkhang.mobile_store.dto.response.product.SimpleProductResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.product_variant.SimpleProductVariantResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrderItemResponse {
    Long id;

    SimpleProductResponseForCustomer product;
    SimpleProductVariantResponse productVariant;
    int quantity;

    double pricePerUnit;
    double total;
}
