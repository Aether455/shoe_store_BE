package com.nguyenkhang.mobile_store.dto.request.user;

import com.nguyenkhang.mobile_store.dto.request.CustomerCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.StaffRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(
        level =
                AccessLevel
                        .PRIVATE)
public class UserCreationRequestForStaff {
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
    @Pattern(regexp = "^(0|\\+84)(\\d{9})$",message = "INVALID_PHONE_NUMBER")
    String phoneNumber;

    @NotNull(message = "POSITION_NOT_EMPTY")
    String position;

    @NotNull(message = "HIRE_DATE_NOT_BLANK")
    @PastOrPresent(message = "HIRE_DATE_INVALID")
    LocalDateTime hireDate;

    @Positive(message = "SALARY_INVALID")
    double salary;
}
