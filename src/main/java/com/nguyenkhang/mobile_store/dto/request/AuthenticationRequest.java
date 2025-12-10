package com.nguyenkhang.mobile_store.dto.request;

import jakarta.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // tao mot builder pattern
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotEmpty(message = "USERNAME_REQUIRED")
    @Schema(description = "Tên đăng nhập", example = "nguyenkhang", requiredMode = Schema.RequiredMode.REQUIRED)
    String username;

    @NotEmpty(message = "PASSWORD_REQUIRED")
    @Schema(
            description = "Mật khẩu người dùng",
            example = "Password@123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 8)
    String password;
}
