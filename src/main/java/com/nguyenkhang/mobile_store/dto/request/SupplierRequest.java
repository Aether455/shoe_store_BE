package com.nguyenkhang.mobile_store.dto.request;

import com.nguyenkhang.mobile_store.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierRequest {

    @NotBlank(message = "SUPPLIER_NAME_REQUIRED")
    String name;
    @NotBlank(message = "ADDRESS_REQUIRED")
    String address;
    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @Pattern(regexp = "^(0|\\+84)(\\d{9})$",message = "INVALID_PHONE_NUMBER")
    String phoneNumber;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL")
    String email;


}
