package com.nguyenkhang.mobile_store.controller;

import java.math.BigDecimal;
import java.util.List;

import com.nguyenkhang.mobile_store.dto.request.DailyReportCriteria;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.response.DailyReportResponse;
import com.nguyenkhang.mobile_store.dto.response.RevenueReportResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.BrandRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.CategoryRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.ProductRevenueResponse;
import com.nguyenkhang.mobile_store.dto.response.statistic.SellingProductResponse;
import com.nguyenkhang.mobile_store.service.DailyReportService;
import com.nguyenkhang.mobile_store.service.StatisticService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/statistics")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StatisticController {
    DailyReportService dailyReportService;
    StatisticService statisticService;

    @GetMapping("/daily-reports")
    public ApiResponse<Page<DailyReportResponse>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<DailyReportResponse>>builder()
                .result(dailyReportService.get(page, size, sortBy))
                .build();
    }
    @GetMapping("/daily-reports/filter")
    public ApiResponse<Page<DailyReportResponse>> filter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            DailyReportCriteria criteria) {
        return ApiResponse.<Page<DailyReportResponse>>builder()
                .result(dailyReportService.filter(page, size, sortBy,criteria))
                .build();
    }

    @GetMapping("/new-orders")
    public ApiResponse<Page<SimpleOrderResponse>> getNewOrder(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<SimpleOrderResponse>>builder()
                .result(statisticService.getNewOrder(page, size))
                .build();
    }

    @GetMapping("/revenue-by-month")
    public ApiResponse<List<RevenueReportResponse>> getRevenueByMonthAndYear() {
        return ApiResponse.<List<RevenueReportResponse>>builder()
                .result(statisticService.getRevenueByMonthAndYear())
                .build();
    }

    @GetMapping("/total-revenue")
    public ApiResponse<BigDecimal> getTotalRevenue() {
        return ApiResponse.<BigDecimal>builder()
                .result(statisticService.getTotalRevenue())
                .build();
    }

    @GetMapping("/revenue-by-product")
    public ApiResponse<List<ProductRevenueResponse>> getRevenueByProduct() {
        return ApiResponse.<List<ProductRevenueResponse>>builder()
                .result(statisticService.getRevenueByProduct())
                .build();
    }

    @GetMapping("/revenue-by-category")
    public ApiResponse<List<CategoryRevenueResponse>> getRevenueByCategory() {
        return ApiResponse.<List<CategoryRevenueResponse>>builder()
                .result(statisticService.getRevenueByCategory())
                .build();
    }

    @GetMapping("/revenue-by-brand")
    public ApiResponse<List<BrandRevenueResponse>> getRevenueByBrand() {
        return ApiResponse.<List<BrandRevenueResponse>>builder()
                .result(statisticService.getRevenueByBrand())
                .build();
    }

    @GetMapping("/top-selling-products")
    public ApiResponse<List<SellingProductResponse>> getTopSellingProducts() {
        return ApiResponse.<List<SellingProductResponse>>builder()
                .result(statisticService.getTopSellingProductsByQuantity())
                .build();
    }

    @GetMapping("/top-revenue-products")
    public ApiResponse<Page<ProductRevenueResponse>> getTopRevenueProducts() {
        return ApiResponse.<Page<ProductRevenueResponse>>builder()
                .result(statisticService.getTopRevenueProducts())
                .build();
    }
}
