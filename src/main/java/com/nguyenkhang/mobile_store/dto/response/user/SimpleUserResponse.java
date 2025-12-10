package com.nguyenkhang.mobile_store.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleUserResponse {
    @Schema(description = "ID nhân viên", example = "21")
    String id;

    @Schema(description = "Tên tài khoản", example = "admin")
    String username;

    @Schema(description = "Email tài khoản", example = "admin@admin.com")
    String email;
}
