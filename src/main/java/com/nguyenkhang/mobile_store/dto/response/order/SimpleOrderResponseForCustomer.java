package com.nguyenkhang.mobile_store.dto.response.order;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleOrderResponseForCustomer {

    Long id;

    String orderCode;

    String phoneNumber;

    String shippingAddress;

    String status;

    String note;

    String receiverName;

    double reducedAmount;

    double totalAmount;

    double finalAmount;

    LocalDateTime createAt;
}
