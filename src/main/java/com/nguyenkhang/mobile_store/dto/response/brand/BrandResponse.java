package com.nguyenkhang.mobile_store.dto.response.brand;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandResponse {

    String id;
    String name;
    String description;

    SimpleUserResponse createBy;

    SimpleUserResponse updateBy;

    LocalDate createAt;

    LocalDate updateAt;

}
