package com.nguyenkhang.mobile_store.dto.response.customer;

import com.nguyenkhang.mobile_store.dto.response.AddressResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerResponseForUser {

    Long id;

    String fullName;

    String phoneNumber;

    List<AddressResponse> addresses;

    LocalDateTime createAt;
}
