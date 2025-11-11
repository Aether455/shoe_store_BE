package com.nguyenkhang.mobile_store.dto.request.order;

import com.nguyenkhang.mobile_store.enums.OrderStatus;
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
public class OrderUpdateRequest {
    String note;
    @NotBlank(message = "RECEIVER_NAME_REQUIRED")
    String receiverName;
    @NotBlank(message = "SHIPPING_ADDRESS_REQUIRED")
    String shippingAddress;
}
