package com.nguyenkhang.mobile_store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreationRequest {
    @NotBlank(message = "CATEGORY_NAME_REQUIRED")
    String name;
    @NotBlank(message = "DESCRIPTION_REQUIRED")
    String description;
}
