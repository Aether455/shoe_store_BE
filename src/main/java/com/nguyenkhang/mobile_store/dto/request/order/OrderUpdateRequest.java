package com.nguyenkhang.mobile_store.dto.request.order;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
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
}
