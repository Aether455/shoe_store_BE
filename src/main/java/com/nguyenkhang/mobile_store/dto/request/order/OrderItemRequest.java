package com.nguyenkhang.mobile_store.dto.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {
    @NotNull(message = "PRODUCT_REQUIRED")
    Long productId;
    @NotNull(message = "PRODUCT_VARIANT_REQUIRED")
    Long productVariantId;

    int quantity;
    double pricePerUnit;
}
