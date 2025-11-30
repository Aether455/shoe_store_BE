package com.nguyenkhang.mobile_store.mapper;

import java.util.List;

import com.nguyenkhang.mobile_store.dto.response.order.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nguyenkhang.mobile_store.dto.request.order.OrderCreationRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderItemRequest;
import com.nguyenkhang.mobile_store.dto.request.order.OrderUpdateRequest;
import com.nguyenkhang.mobile_store.entity.Order;
import com.nguyenkhang.mobile_store.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toOrder(OrderCreationRequest request);

    OrderResponse toOrderResponse(Order order);

    OrderResponseForCustomer toOrderResponseForCustomer(Order order);

    List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> items);

    SimpleOrderResponse toSimpleOrderResponse(Order order);

    SimpleOrderResponseForCustomer toSimpleOrderResponseForCustomer(Order order);

    @Mapping(target = "totalPrice", expression = "java(request.getPricePerUnit() * request.getQuantity())")
    OrderItem toOrderItem(OrderItemRequest request);

    void updateOrder(@MappingTarget Order order, OrderUpdateRequest request);
}
