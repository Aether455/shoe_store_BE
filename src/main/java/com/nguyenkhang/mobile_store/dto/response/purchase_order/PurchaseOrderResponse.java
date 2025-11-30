package com.nguyenkhang.mobile_store.dto.response.purchase_order;

import java.time.LocalDateTime;
import java.util.List;

import com.nguyenkhang.mobile_store.dto.response.SimpleSupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.SimpleWarehouseResponse;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.WarehouseResponse;
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
public class PurchaseOrderResponse {

    Long id;

    SimpleSupplierResponse supplier;

    SimpleWarehouseResponse warehouse;

    List<PurchaseOrderItemResponse> purchaseOrderItems;

    PurchaseOrderStatus status;

    double totalAmount;

    SimpleUserResponse createBy;
    SimpleUserResponse updateBy;

    LocalDateTime createAt;
    LocalDateTime updateAt;

}
