package com.nguyenkhang.mobile_store.dto.response.order;

import java.time.LocalDateTime;
import java.util.List;

import com.nguyenkhang.mobile_store.dto.response.SimpleWarehouseResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.SimpleCustomerResponse;
import com.nguyenkhang.mobile_store.dto.response.payment.PaymentResponse;
import com.nguyenkhang.mobile_store.dto.response.user.SimpleUserResponse;
import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.mobile_store.enums.OrderStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    Long id;

    String orderCode;
    String receiverName;
    String shippingAddress;
    String phoneNumber;
    OrderStatus status;
    String note;
    double reducedAmount;
    double totalAmount;
    double finalAmount;

    VoucherResponse voucher;
    SimpleCustomerResponse customer;

    List<OrderItemResponse> orderItems;
    PaymentResponse payment;

    List<OrderStatusHistoryResponse> orderStatusHistories;

    SimpleWarehouseResponse warehouse;

    LocalDateTime createAt;
    LocalDateTime updateAt;
    SimpleUserResponse createBy;
    SimpleUserResponse updateBy;
}
