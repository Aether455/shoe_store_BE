package com.nguyenkhang.mobile_store.dto.request;

import com.nguyenkhang.mobile_store.entity.User;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffRequest {

    @NotBlank(message = "USER_NOT_BLANK")
    Long userId;

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
