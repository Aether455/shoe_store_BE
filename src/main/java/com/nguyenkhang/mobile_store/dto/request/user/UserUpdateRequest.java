package com.nguyenkhang.mobile_store.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(
        level =
                AccessLevel
                        .PRIVATE)
public class UserUpdateRequest {

    String password;
    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL")
    String email;

    List<String> roles;
}
