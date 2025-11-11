package com.nguyenkhang.mobile_store.dto.request.user;

import com.nguyenkhang.mobile_store.dto.request.CustomerCreationRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(
        level =
                AccessLevel
                        .PRIVATE)
public class UserCreationRequestForCustomer {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL")
    String email;

    @NotBlank(message = "FULL_NAME_REQUIRED")
    String fullName;

    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @Pattern(regexp =  "^(0|\\+84)(\\d{9})$",message = "INVALID_PHONE_NUMBER")
    String phoneNumber;

}
