package com.nguyenkhang.mobile_store.controller.user;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.order.OrderCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateStatusRequest;
import com.nguyenkhang.mobile_store.dto.response.order.OrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.OrderResponseForCustomer;
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
@RequestMapping("/user/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserOrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Valid OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<OrderResponseForCustomer> getOrderById(@PathVariable long id) {
        return ApiResponse.<OrderResponseForCustomer>builder()
                .result(orderService.getOrderByIdForCustomer(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderResponse> update(@RequestBody @Valid OrderUpdateRequest request, @PathVariable long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.update(id, request))
                .build();
    }

    @PatchMapping("/{id}/confirm")
    public ApiResponse<OrderResponse> confirmOrder(@PathVariable long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.confirmOrder(id))
                .build();
    }

    @PatchMapping("/{id}/cancel")
    public ApiResponse<OrderResponse> cancelOrder(@PathVariable long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.cancelOrder(id))
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<Page<SimpleOrderResponseForCustomer>> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponse.<Page<SimpleOrderResponseForCustomer>>builder()
                .result(orderService.getMyOrders(page, size, sortBy))
                .build();
    }

}
