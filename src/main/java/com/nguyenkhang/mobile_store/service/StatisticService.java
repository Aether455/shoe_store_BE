package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.response.RevenueReportResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.BrandRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.CategoryRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.ProductRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.SellingProductResponse;
import com.nguyenkhang.mobile_store.enums.OrderStatus;
import com.nguyenkhang.mobile_store.mapper.OrderMapper;
import com.nguyenkhang.mobile_store.repository.CustomerRepository;
import com.nguyenkhang.mobile_store.repository.DailyReportRepository;
import com.nguyenkhang.mobile_store.repository.OrderItemRepository;
import com.nguyenkhang.mobile_store.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticService {
    DailyReportRepository dailyReportRepository;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    CustomerRepository customerRepository;
    OrderMapper orderMapper;

    public List<RevenueReportResponse> getRevenueByMonthAndYear(){
        return orderRepository.reportRevenueByMonth(OrderStatus.COMPLETED);
    }

    public BigDecimal getTotalRevenue(){
        return orderRepository.getTotalRevenue(OrderStatus.COMPLETED);
    }

    public Page<SimpleOrderResponse> getNewOrder(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        return orderRepository.findAllByCreateAt(LocalDate.now(),pageable).map(orderMapper::toSimpleOrderResponse);
    }

    public List<ProductRevenueResponse> getRevenueByProduct(){
        return orderItemRepository.findRevenueByProduct();
    }

    public List<CategoryRevenueResponse> getRevenueByCategory(){
        return orderItemRepository.findRevenueByCategory();
    }

    public List<BrandRevenueResponse> getRevenueByBrand(){
        return orderItemRepository.findRevenueByBrand();
    }

    public List<SellingProductResponse> getTopSellingProductsByQuantity(){

        return orderItemRepository.findTopSellingProductsByQuantity();
    }

    public Page<ProductRevenueResponse> getTopRevenueProducts(){
        Pageable pageable = PageRequest.of(0, 10);
        return orderItemRepository.findTopRevenueProducts(pageable);
    }
}
