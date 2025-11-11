package com.nguyenkhang.mobile_store.dto.request;

import com.nguyenkhang.mobile_store.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddressRequest {

    @NotBlank(message = "CUSTOMER_ID_REQUIRED")
    long customerId;
    @NotBlank(message = "ADDRESS_REQUIRED")
    String address;

    @NotBlank(message = "PROVINCE_REQUIRED")
    String province;

    @NotBlank(message = "DISTRICT_REQUIRED")
    String district;

    @NotBlank(message = "WARD_REQUIRED")
    String ward;

}
