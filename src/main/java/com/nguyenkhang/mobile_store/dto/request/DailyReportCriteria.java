package com.nguyenkhang.mobile_store.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DailyReportCriteria {

    BigDecimal totalRevenueStart;
    BigDecimal totalRevenueEnd;

    BigDecimal avgOrderValueStart;
    BigDecimal avgOrderValueEnd;



    LocalDate reportDateStart;
    LocalDate reportDateEnd;
}
