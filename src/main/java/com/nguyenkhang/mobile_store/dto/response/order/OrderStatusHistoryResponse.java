package com.nguyenkhang.mobile_store.dto.response.order;

import com.nguyenkhang.mobile_store.dto.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatusHistoryResponse {

    Long id;

    String oldStatus;
    String newStatus;

    UserResponse changeBy;

    LocalDateTime changeAt;
}
