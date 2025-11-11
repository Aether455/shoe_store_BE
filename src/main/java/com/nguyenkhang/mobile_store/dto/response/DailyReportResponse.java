package com.nguyenkhang.mobile_store.dto.response;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DailyReportResponse {

    Long id;

    LocalDate reportDate;

    BigDecimal totalRevenue;

    int totalOrders;

    BigDecimal avgOrderValue;

    int totalItemsSold;

    int newCustomersCount;

    BigDecimal totalDiscountAmount;

}
