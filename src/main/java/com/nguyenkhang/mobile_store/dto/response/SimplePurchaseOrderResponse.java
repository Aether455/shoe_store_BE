package com.nguyenkhang.mobile_store.dto.response;

import java.time.LocalDateTime;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;

import com.nguyenkhang.mobile_store.enums.PurchaseOrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePurchaseOrderResponse {

    Long id;

    SimpleSupplierResponse supplier;

    SimpleWarehouseResponse warehouse;

    PurchaseOrderStatus status;
    double totalAmount;

    SimpleUserResponse createBy;

    LocalDateTime createAt;
}
