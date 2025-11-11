package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.dto.response.statistic.BrandRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.CategoryRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.ProductRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.SellingProductResponse;
import com.nguyenkhang.mobile_store.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    List<OrderItem> findAllByOrderIdIn(Iterable<Long> orderIds);

    @Query("SELECT new com.nguyenkhang.mobile_store.dto.response.statistic.ProductRevenueResponse(" +
            "  p.id, " +
            " p.mainImageUrl,"+
            "  p.name, " +
            "  SUM(oi.totalPrice)) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN oi.order o " +
            "WHERE o.status = 'COMPLETED' " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(oi.totalPrice) DESC")
    List<ProductRevenueResponse> findRevenueByProduct();

    @Query("SELECT new com.nguyenkhang.mobile_store.dto.response.statistic.CategoryRevenueResponse(" +
            "  c.id, " +
            "  c.name, " +
            "  SUM(oi.totalPrice)) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN p.category c " +
            "JOIN oi.order o " +
            "WHERE o.status = 'COMPLETED' " +
            "GROUP BY c.id, c.name " +
            "ORDER BY SUM(oi.totalPrice) DESC")
    List<CategoryRevenueResponse>  findRevenueByCategory();

    @Query("SELECT new com.nguyenkhang.mobile_store.dto.response.statistic.BrandRevenueResponse(" +
            "  b.id, " +
            "  b.name, " +
            "  SUM(oi.totalPrice)) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN p.brand b " +
            "JOIN oi.order o " +
            "WHERE o.status = 'COMPLETED' " +
            "GROUP BY b.id, b.name " +
            "ORDER BY SUM(oi.totalPrice) DESC")
    List<BrandRevenueResponse> findRevenueByBrand();


    @Query("SELECT new com.nguyenkhang.mobile_store.dto.response.statistic.SellingProductResponse(" +
            "  p.id, " +
            " p.mainImageUrl,"+
            "  p.name, " +
            "  SUM(oi.quantity)) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN oi.order o " +
            "WHERE o.status = 'COMPLETED' " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<SellingProductResponse> findTopSellingProductsByQuantity();

    @Query("SELECT new com.nguyenkhang.mobile_store.dto.response.statistic.ProductRevenueResponse(" +
            "  p.id, " +
            " p.mainImageUrl,"+
            "  p.name, " +
            "  SUM(oi.totalPrice)) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN oi.order o " +
            "WHERE o.status = 'COMPLETED' " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(oi.totalPrice) DESC")
    Page<ProductRevenueResponse> findTopRevenueProducts(Pageable pageable);


}
