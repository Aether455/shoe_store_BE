package com.nguyenkhang.mobile_store.dto.response.purchase_order;

import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.WarehouseResponse;
import com.nguyenkhang.mobile_store.enums.PurchaseOrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrderResponse {

    Long id;

    SupplierResponse supplier;

    WarehouseResponse warehouse;

    List<PurchaseOrderItemResponse> purchaseOrderItems;

    PurchaseOrderStatus status;

    double totalAmount;


    SimpleUserResponse createBy;

    LocalDateTime createAt;
}
