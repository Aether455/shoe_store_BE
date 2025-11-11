package com.nguyenkhang.mobile_store.dto.response;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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


    SimpleUserResponse createBy;

    SimpleUserResponse updateBy;

    LocalDateTime createAt;
    LocalDateTime updateAt;
}
