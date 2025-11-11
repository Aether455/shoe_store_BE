package com.nguyenkhang.mobile_store.dto.response.product_variant;

import com.nguyenkhang.mobile_store.dto.response.option.SimpleOptionValueResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class ProductVariantResponseForCustomer {


    Long id;

    String productVariantImageUrl;

    Set<SimpleOptionValueResponse> optionValues;

    double price;

    int quantity;


}
