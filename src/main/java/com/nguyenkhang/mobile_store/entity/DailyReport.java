package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class DailyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @Column(unique = true, nullable = false)
    LocalDate reportDate;

    @Column(nullable = false,precision = 15,scale = 2)
    BigDecimal totalRevenue;

    @Column(nullable = false,columnDefinition = "INT DEFAULT 0")
    int totalOrders;

    @Column(nullable = false,precision = 15,scale = 2)
    BigDecimal avgOrderValue;

    @Column(nullable = false,columnDefinition = "INT DEFAULT 0")
    int totalItemsSold;

    @Column(nullable = false,columnDefinition = "INT DEFAULT 0")
    int newCustomersCount;

    @Column(nullable = false,precision = 15,scale = 2)
    BigDecimal totalDiscountAmount;

}
