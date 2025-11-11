package com.nguyenkhang.mobile_store.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    @NotNull(message = "PRODUCT_VARIANT_REQUIRED")
    long productVariantId;
    @NotNull(message = "PRODUCT_REQUIRED")
    long productId;

    @PositiveOrZero(message = "CART_ITEM_QUANTITY_INVALID")
    int quantity;
}
