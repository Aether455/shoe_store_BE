package com.nguyenkhang.mobile_store.dto.response.statistic;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRevenueResponse {
    Long productId;
    String mainImageUrl;
    String productName;
    Double totalRevenue;
}
