package com.nguyenkhang.mobile_store.dto.request;

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
public class StaffUpdateRequest {


    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "^(0|\\+84)(\\d{9})$", message = "INVALID_PHONE_NUMBER")
    String phoneNumber;

    @NotBlank(message = "FULL_NAME_REQUIRED")
    String fullName;

    @NotBlank(message = "GENDER_REQUIRED")
    String gender;


    @NotNull(message = "POSITION_NOT_EMPTY")
    String position;


    @Positive(message = "SALARY_INVALID")
    double salary;
}
