package com.nguyenkhang.mobile_store.dto.request.products;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class VariantCreationRequest {

    @NotBlank(message = "SKU_REQUIRED")
    String sku;

    String productVariantImageUrl;
    MultipartFile imageFile;

    @NotEmpty(message = "OPTION_VALUES_REQUIRED")
    Set<Long> optionValues;

    @Positive(message = "PRODUCT_PRICE_INVALID")
    @NotNull(message = "PRODUCT_PRICE_REQUIRED")
    double price;

    @PositiveOrZero(message = "QUANTITY_INVALID")
    @NotNull(message = "QUANTITY_REQUIRED")
    int quantity;
}
