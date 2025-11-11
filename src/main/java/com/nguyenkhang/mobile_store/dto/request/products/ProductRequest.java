package com.nguyenkhang.mobile_store.dto.request.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "PRODUCT_NAME_NOT_BLANK")
    String name;

    MultipartFile mainImageFile;

    @NotBlank(message = "PRODUCT_DESCRIPTION_NOT_BLANK")
    String description;

    @NotNull(message = "CATEGORY_NOT_NULL")
    long categoryId;

    @NotNull(message = "BRAND_NOT_NULL")
    long brandId;
}
