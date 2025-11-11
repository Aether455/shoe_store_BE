package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.order.OrderCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateStatusRequest;
import com.nguyenkhang.mobile_store.dto.response.order.OrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponseForCustomer;
import com.nguyenkhang.mobile_store.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreationRequest request){
        return ApiResponse.<OrderResponse>builder().result(orderService.createOrder(request)).build();
    }

    @GetMapping
    public ApiResponse<Page<OrderResponse>> getOrders(
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "id") String sortBy){
        return ApiResponse.<Page<OrderResponse>>builder().result(orderService.getOrders(page, size, sortBy)).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrders(@PathVariable long id){
        return ApiResponse.<OrderResponse>builder().result(orderService.getOrderById(id)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderResponse> update(@RequestBody @Valid OrderUpdateRequest request, @PathVariable long id){
        return ApiResponse.<OrderResponse>builder().result(orderService.update(id,request)).build();
    }
    @PutMapping("/{id}/status")
    public ApiResponse<OrderResponse> updateStatus(@RequestBody @Valid OrderUpdateStatusRequest request, @PathVariable long id){
        return ApiResponse.<OrderResponse>builder().result(orderService.updateStatus(id,request)).build();
    }

    @PatchMapping("/{id}/confirm")
    public ApiResponse<OrderResponse> confirmOrder( @PathVariable long id){
        return ApiResponse.<OrderResponse>builder().result(orderService.confirmOrder(id)).build();
    }
    @PatchMapping("/{id}/cancel")
    public ApiResponse<OrderResponse> cancelOrder( @PathVariable long id){
        return ApiResponse.<OrderResponse>builder().result(orderService.cancelOrder(id)).build();
    }

    @GetMapping("/me")
    public ApiResponse<Page<SimpleOrderResponseForCustomer>> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy){
        return ApiResponse.<Page<SimpleOrderResponseForCustomer>>builder().result(orderService.getMyOrders(page, size, sortBy)).build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<SimpleOrderResponse>> searchOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String keyword){
        return ApiResponse.<Page<SimpleOrderResponse>>builder().result(orderService.searchOrders(keyword, page)).build();
    }
}
