package com.nguyenkhang.mobile_store.dto.response;

import java.time.LocalDateTime;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseResponse {

    Long id;

    String name;
    String address;

    String description;
    int priority;
    String province;
    String district;
    String ward;

    SimpleUserResponse createBy;

    SimpleUserResponse updateBy;

    LocalDateTime createAt;
    LocalDateTime updateAt;
}
