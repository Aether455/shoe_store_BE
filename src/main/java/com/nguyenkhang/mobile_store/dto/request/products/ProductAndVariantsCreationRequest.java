package com.nguyenkhang.mobile_store.dto.request.products;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAndVariantsCreationRequest {

    @NotBlank(message = "PRODUCT_NAME_NOT_BLANK")
    String name;

    @NotBlank(message = "PRODUCT_DESCRIPTION_NOT_BLANK")
    String description;
    @NotNull(message = "MAIN_IMAGE_FILE_REQUIRED")
    MultipartFile mainImageFile;

    List<VariantCreationRequest> variants;

    @NotNull(message = "CATEGORY_NOT_NULL")
    long categoryId;

    @NotNull(message = "BRAND_NOT_NULL")
    long brandId;
}
