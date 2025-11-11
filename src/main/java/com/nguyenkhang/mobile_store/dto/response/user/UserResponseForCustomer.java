package com.nguyenkhang.mobile_store.dto.response.user;

import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseForCustomer {
    String id;
    String username;
    String email;

    LocalDate createAt;

    CustomerResponse customer;
}
