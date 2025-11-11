package com.nguyenkhang.mobile_store.dto.response.purchase_order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrderReportResponse {
    String label;
    BigDecimal totalAmount;
    Long orderCount;
}
