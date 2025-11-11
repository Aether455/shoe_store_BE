package com.nguyenkhang.mobile_store.dto.response.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponseForCustomer {

    String id;
    String name;
    String description;

}
