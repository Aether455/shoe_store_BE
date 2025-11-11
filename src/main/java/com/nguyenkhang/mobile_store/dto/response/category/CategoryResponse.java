package com.nguyenkhang.mobile_store.dto.response.category;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {

    String id;
    String name;
    String description;
    LocalDate createAt;
    LocalDate updateAt;
    SimpleUserResponse createBy;

    SimpleUserResponse updateBy;
}
