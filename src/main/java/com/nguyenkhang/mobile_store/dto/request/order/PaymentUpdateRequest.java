package com.nguyenkhang.mobile_store.dto.request.order;

import com.nguyenkhang.mobile_store.enums.PaymentMethod;
import com.nguyenkhang.mobile_store.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentUpdateRequest {
    long orderId;
    PaymentStatus status;
}
