package com.nguyenkhang.mobile_store.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "SUPPLIER_REQUIRED")
    Long supplierId;


    @NotNull(message = "WAREHOUSE_REQUIRED")
    Long warehouseId;

    List<PurchaseOrderItemRequest> purchaseOrderItems;

    @Positive(message = "TOTAL_PRICE_INVALID")
    @NotNull(message = "TOTAL_PRICE_REQUIRED")
    BigDecimal totalAmount;
}
