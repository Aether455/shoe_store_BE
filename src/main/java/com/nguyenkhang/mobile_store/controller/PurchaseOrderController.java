package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.PurchaseOrderRequest;
import com.nguyenkhang.mobile_store.dto.response.SupplierResponse;
import com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderReportResponse;
import com.nguyenkhang.mobile_store.dto.response.purchase_order.PurchaseOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.SimplePurchaseOrderResponse;
import com.nguyenkhang.mobile_store.service.PurchaseOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseOrderController {
    PurchaseOrderService purchaseOrderService;

    @PostMapping
    public ApiResponse<SimplePurchaseOrderResponse> create(@RequestBody PurchaseOrderRequest request) {
        return ApiResponse.<SimplePurchaseOrderResponse>builder().result(purchaseOrderService.create(request)).build();
    }

    @PatchMapping("/{id}/approve")
    public ApiResponse<PurchaseOrderResponse> approve(@PathVariable long id) {
        return ApiResponse.<PurchaseOrderResponse>builder().result(purchaseOrderService.approve(id)).build();
    }

    @PatchMapping("/{id}/cancel")
    public ApiResponse<PurchaseOrderResponse> cancel(@PathVariable long id) {
        return ApiResponse.<PurchaseOrderResponse>builder().result(purchaseOrderService.cancel(id)).build();
    }

    @GetMapping
    public ApiResponse<Page<SimplePurchaseOrderResponse>> get(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<SimplePurchaseOrderResponse>>builder().result(purchaseOrderService.get(page, size, sortBy)).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PurchaseOrderResponse> getById(@PathVariable long id) {
        return ApiResponse.<PurchaseOrderResponse>builder().result(purchaseOrderService.getById(id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable long id) {
        return ApiResponse.<String>builder().result(purchaseOrderService.delete(id)).build();
    }

    @GetMapping("/reports")
    public ApiResponse<List<PurchaseOrderReportResponse>> reports(@RequestParam LocalDate start,
                                                            @RequestParam LocalDate end,
                                                            @RequestParam String groupBy){
        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.atTime(23,59,59);
        return ApiResponse.<List<PurchaseOrderReportResponse>>builder().result(purchaseOrderService.report(startDate, endDate, groupBy)).build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<SimplePurchaseOrderResponse>> searchSupplier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String keyword){
        return ApiResponse.<Page<SimplePurchaseOrderResponse>>builder().result(purchaseOrderService.searchPurchaseOrder(keyword, page)).build();
    }
}
