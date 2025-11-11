package com.nguyenkhang.mobile_store.dto.response.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleCategoryResponse {

    String id;
    String name;
    String description;

}
