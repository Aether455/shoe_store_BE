package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.dto.response.RevenueReportResponse;
import com.nguyenkhang.mobile_store.entity.Order;
import com.nguyenkhang.mobile_store.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    Page<Order> findAllByUserId(long userId, Pageable pageable);

    @Query("select o from Order o where o.status = :status and DATE(o.createAt) = :date")
    List<Order> findAllByStatusAndCreateAt(@Param("status") OrderStatus status, @Param("date") LocalDate date);

    @Query("select new com.nguyenkhang.mobile_store.dto.response.RevenueReportResponse(" +
            " CONCAT(YEAR(o.createAt), '-', LPAD(STR(MONTH(o.createAt)), 2, '0')) ," +
            " SUM(o.finalAmount)" +
            ") from Order o where o.status = :status " +
            " GROUP BY YEAR(o.createAt), FUNCTION('MONTH', o.createAt), CONCAT(YEAR(o.createAt), '-', LPAD(STR(MONTH(o.createAt)), 2, '0'))" +
            " ORDER BY YEAR(o.createAt), FUNCTION('MONTH', o.createAt)")
    List<RevenueReportResponse> reportRevenueByMonth(@Param("status")  OrderStatus status);

    @Query("select SUM(o.finalAmount) from Order o where o.status = :status")
    BigDecimal getTotalRevenue(@Param("status") OrderStatus status);

    @Query("select o from Order o where MONTH(o.createAt) = MONTH(:date)")
    Page<Order> findAllByCreateAt(@Param("date") LocalDate date, Pageable pageable);
}
