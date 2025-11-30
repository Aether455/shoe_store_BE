package com.nguyenkhang.mobile_store.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.nguyenkhang.mobile_store.dto.request.DailyReportCriteria;
import com.nguyenkhang.mobile_store.specification.DailyReportSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.nguyenkhang.mobile_store.dto.response.DailyReportResponse;
import com.nguyenkhang.mobile_store.entity.DailyReport;
import com.nguyenkhang.mobile_store.entity.Order;
import com.nguyenkhang.mobile_store.entity.OrderItem;
import com.nguyenkhang.mobile_store.enums.OrderStatus;
import com.nguyenkhang.mobile_store.mapper.DailyReportMapper;
import com.nguyenkhang.mobile_store.repository.CustomerRepository;
import com.nguyenkhang.mobile_store.repository.DailyReportRepository;
import com.nguyenkhang.mobile_store.repository.OrderItemRepository;
import com.nguyenkhang.mobile_store.repository.OrderRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DailyReportService {
    DailyReportRepository dailyReportRepository;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    CustomerRepository customerRepository;

    DailyReportMapper dailyReportMapper;

    @Async
    @Scheduled(cron = "2 0 0 *  * ?")
    public void autoCreateDailyReport() {
        LocalDate reportDate = LocalDate.now().minusDays(1);

        List<Order> dailyOrders = orderRepository.findAllByStatusAndCreateAt(OrderStatus.COMPLETED, reportDate);

        BigDecimal totalRevenue = dailyOrders.stream()
                .map((order) -> BigDecimal.valueOf(order.getFinalAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalOrders = dailyOrders.size();

        BigDecimal totalDiscountAmount = dailyOrders.stream()
                .map((e) -> BigDecimal.valueOf(e.getReducedAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avgOrderValue;

        if (totalOrders == 0) {
            avgOrderValue = BigDecimal.ZERO;
        } else {
            avgOrderValue = totalRevenue.divide(BigDecimal.valueOf(totalOrders), 3, RoundingMode.DOWN);
        }

        Set<Long> orderIds = dailyOrders.stream().map(Order::getId).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderIdIn(orderIds);

        int totalItemsSold =
                orderItemList.stream().mapToInt(OrderItem::getQuantity).sum();

        Integer newCustomersCount = customerRepository.countByCreateAt(reportDate);

        DailyReport newDailyreport = DailyReport.builder()
                .reportDate(reportDate)
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .avgOrderValue(avgOrderValue)
                .totalItemsSold(totalItemsSold)
                .newCustomersCount(newCustomersCount)
                .totalDiscountAmount(totalDiscountAmount)
                .build();

        dailyReportRepository.save(newDailyreport);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<DailyReportResponse> get(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var dailyReports = dailyReportRepository.findAll(pageable);

        return dailyReports.map(dailyReportMapper::toDailyReportResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Page<DailyReportResponse> filter(int page, int size, String sortBy, DailyReportCriteria criteria) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        var spec = DailyReportSpecification.createSpecification(criteria);
        var dailyReports = dailyReportRepository.findAll(spec, pageable);

        return dailyReports.map(dailyReportMapper::toDailyReportResponse);
    }
}
