package com.nguyenkhang.mobile_store.dto.response.option;

import com.nguyenkhang.mobile_store.dto.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OptionResponse {
    Long id;

    String name;

    List<OptionValueResponse> optionValues;

    UserResponse createBy;

    UserResponse updateBy;

    LocalDateTime createAt;

    LocalDateTime updateAt;
}
