package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderReportResponse;
import com.nguyenkhang.mobile_store.entity.PurchaseOrder;
import com.nguyenkhang.mobile_store.entity.Supplier;
import com.nguyenkhang.mobile_store.enums.PurchaseOrderStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long>, JpaSpecificationExecutor<PurchaseOrder> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM PurchaseOrder p WHERE p.id = :id")
    Optional<PurchaseOrder> findByIdForUpdate(@Param("id") long id);

    @Query("SELECT new com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderReportResponse(" +
            "po.supplier.name, " +
            "SUM(po.totalAmount)," +
            "COUNT(po)) " +
            "FROM PurchaseOrder po " +
            "WHERE po.status = 'APPROVED'" +
            "AND po.createAt BETWEEN :start AND :end GROUP BY po.supplier")
    List<PurchaseOrderReportResponse> reportBySupplier(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

        @Query("""
                SELECT new com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderReportResponse(\
                 CONCAT(YEAR(po.createAt), '-', LPAD(STR(MONTH(po.createAt)), 2, '0')),\
                 SUM(po.totalAmount),\
                 COUNT(po)\
                ) FROM PurchaseOrder po\
                 WHERE po.status = 'APPROVED'\
                 AND po.createAt BETWEEN :start AND :end\
                 GROUP BY YEAR(po.createAt), FUNCTION('MONTH', po.createAt),CONCAT(YEAR(po.createAt), '-', LPAD(STR(MONTH(po.createAt)), 2, '0'))\
                 ORDER BY YEAR(po.createAt), FUNCTION('MONTH', po.createAt)""")
        List<PurchaseOrderReportResponse> reportByMonth(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
            SELECT new com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderReportResponse(\
            CONCAT('Q', QUARTER(po.createAt),'-',YEAR(po.createAt)),\
            SUM(po.totalAmount), \
            COUNT(po))\
            FROM PurchaseOrder po \
            WHERE po.status = 'APPROVED'\
            AND  po.createAt BETWEEN :start AND :end \
            GROUP BY YEAR(po.createAt), QUARTER(po.createAt) ,CONCAT('Q', QUARTER(po.createAt),'-',YEAR(po.createAt))\
            ORDER BY YEAR(po.createAt), QUARTER(po.createAt)""")
    List<PurchaseOrderReportResponse> reportByQuarter(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
