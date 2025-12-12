package com.nguyenkhang.mobile_store.controller.user;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.nguyenkhang.mobile_store.dto.ApiResponseDTO;
import com.nguyenkhang.mobile_store.dto.request.order.OrderCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.order.OrderResponse;
import com.nguyenkhang.mobile_store.dto.response.order.OrderResponseForCustomer;
import com.nguyenkhang.mobile_store.dto.response.order.SimpleOrderResponseForCustomer;
import com.nguyenkhang.mobile_store.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserOrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponseDTO<OrderResponse> createOrder(@RequestBody @Valid OrderCreationRequest request) {
        return ApiResponseDTO.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponseDTO<OrderResponseForCustomer> getOrderById(@PathVariable long id) {
        return ApiResponseDTO.<OrderResponseForCustomer>builder()
                .result(orderService.getOrderByIdForCustomer(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponseDTO<OrderResponse> update(@RequestBody @Valid OrderUpdateRequest request, @PathVariable long id) {
        return ApiResponseDTO.<OrderResponse>builder()
                .result(orderService.update(id, request))
                .build();
    }

    @PatchMapping("/{id}/confirm")
    public ApiResponseDTO<OrderResponse> confirmOrder(@PathVariable long id) {
        return ApiResponseDTO.<OrderResponse>builder()
                .result(orderService.confirmOrder(id))
                .build();
    }

    @PatchMapping("/{id}/cancel")
    public ApiResponseDTO<OrderResponse> cancelOrder(@PathVariable long id) {
        return ApiResponseDTO.<OrderResponse>builder()
                .result(orderService.cancelOrder(id))
                .build();
    }

    @PatchMapping("/{id}/complete")
    public ApiResponseDTO<OrderResponse> completeOrder(@PathVariable long id) {
        return ApiResponseDTO.<OrderResponse>builder()
                .result(orderService.completeOrder(id))
                .build();
    }

    @GetMapping("/me")
    public ApiResponseDTO<Page<SimpleOrderResponseForCustomer>> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ApiResponseDTO.<Page<SimpleOrderResponseForCustomer>>builder()
                .result(orderService.getMyOrders(page, size, sortBy))
                .build();
    }
}
