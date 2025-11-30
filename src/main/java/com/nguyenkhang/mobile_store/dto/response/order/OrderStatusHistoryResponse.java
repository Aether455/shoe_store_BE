package com.nguyenkhang.mobile_store.dto.response.order;

import java.time.LocalDateTime;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import com.nguyenkhang.mobile_store.dto.response.user.UserResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    SimpleUserResponse changeBy;

    LocalDateTime changeAt;
}
