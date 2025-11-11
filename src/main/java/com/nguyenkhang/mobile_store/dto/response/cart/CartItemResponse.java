package com.nguyenkhang.mobile_store.dto.response.cart;

import com.nguyenkhang.mobile_store.dto.response.option.SimpleOptionValueResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    long id;

    long productId;
    String productName;

    String sku;
    double price;
    Set<SimpleOptionValueResponse> optionValues;

    long productVariantId;

    String productImageUrl;

    int quantity;

    double totalPrice;
}
