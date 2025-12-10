package com.nguyenkhang.mobile_store.dto.request;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {

    @NotBlank(message = "BRAND_NAME_REQUIRED")
    @Schema(description = "Tên thương hiệu", example = "Gucci", requiredMode = Schema.RequiredMode.REQUIRED)
    String name;

    @NotBlank(message = "DESCRIPTION_REQUIRED")
    @Schema(description = "Mô tả", example = "Mô tả của thương hiệu", requiredMode = Schema.RequiredMode.REQUIRED)
    String description;
}
