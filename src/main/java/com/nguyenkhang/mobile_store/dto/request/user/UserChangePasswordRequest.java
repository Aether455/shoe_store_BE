package com.nguyenkhang.mobile_store.dto.request.user;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserChangePasswordRequest {
    String password;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String confirmationPassword;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String newPassword;
}
