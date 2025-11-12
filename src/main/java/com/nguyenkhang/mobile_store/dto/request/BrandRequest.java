package com.nguyenkhang.mobile_store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandRequest {

    @NotBlank(message = "BRAND_NAME_REQUIRED")
    String name;
    @NotBlank(message = "DESCRIPTION_REQUIRED")
    String description;


}
