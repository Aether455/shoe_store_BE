package com.nguyenkhang.mobile_store.dto.response.product_variant;

import java.util.Set;

import com.nguyenkhang.mobile_store.dto.response.option.SimpleOptionValueResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleProductVariantResponse {

    Long id;

    String sku;

    String productVariantImageUrl;

    Set<SimpleOptionValueResponse> optionValues;

    double price;

    int quantity;
}
