package com.nguyenkhang.mobile_store.dto.request.products;

import java.util.Set;

import jakarta.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantCreationRequest {

    @NotBlank(message = "SKU_REQUIRED")
    String sku;

    @NotNull(message = "VARIANT_IMAGE_FILE_REQUIRED")
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
