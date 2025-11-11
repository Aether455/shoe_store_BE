package com.nguyenkhang.mobile_store.dto.request.order;

import com.nguyenkhang.mobile_store.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateStatusRequest {
    OrderStatus orderStatus;
}
