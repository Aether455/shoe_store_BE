package com.nguyenkhang.mobile_store.dto.response.user;

import com.nguyenkhang.mobile_store.dto.response.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    LocalDate createAt;
    LocalDate updateAt;

    Set<RoleResponse> roles;
}
