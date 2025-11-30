package com.nguyenkhang.mobile_store.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.order.OrderCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateStatusRequest;
import com.nguyenkhang.mobile_store.dto.response.order.OrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponseForCustomer;
import com.nguyenkhang.mobile_store.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrderForAdmin(@RequestBody @Valid OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrderForAdmin(request))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<SimpleOrderResponse>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<SimpleOrderResponse>>builder()
                .result(orderService.getOrders(page, size ))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderResponse> update(@RequestBody @Valid OrderUpdateRequest request, @PathVariable long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.update(id, request))
                .build();
    }

    @PutMapping("/{id}/status")
    public ApiResponse<OrderResponse> updateStatus(
            @RequestBody @Valid OrderUpdateStatusRequest request, @PathVariable long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateStatus(id, request))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<SimpleOrderResponse>> searchOrders(
            @RequestParam(defaultValue = "0") int page, @RequestParam String keyword,   @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<SimpleOrderResponse>>builder()
                .result(orderService.searchOrders(keyword, page,size))
                .build();
    }
}
