package com.nguyenkhang.mobile_store.dto.request.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {

    long userId;
    long customerId;

    @NotBlank(message = "PHONE_NUMBER_REQUIRED")
    @Pattern(regexp =  "^(0|\\+84)(\\d{9})$",message = "INVALID_PHONE_NUMBER")
    String phoneNumber;

    String note;

    @NotBlank(message = "RECEIVER_NAME_REQUIRED")
    String receiverName;

    @NotBlank(message = "SHIPPING_ADDRESS_REQUIRED")
    String shippingAddress;

    @NotBlank(message = "PROVINCE_REQUIRED")
    String province;

    @NotBlank(message = "DISTRICT_REQUIRED")
    String district;

    @NotBlank(message = "WARD_REQUIRED")
    String ward;

    String voucherCode;

    PaymentRequest payment;
    List<OrderItemRequest> orderItems;
    double totalAmount;

}
