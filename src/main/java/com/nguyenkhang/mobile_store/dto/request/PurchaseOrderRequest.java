package com.nguyenkhang.mobile_store.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrderRequest {

    Long supplierId;


    Long warehouseId;

    List<PurchaseOrderItemRequest> purchaseOrderItems;

    BigDecimal totalAmount;
}
