package com.nguyenkhang.mobile_store.dto.response.order;

import com.nguyenkhang.mobile_store.dto.response.vouchers.VoucherResponse;
import com.nguyenkhang.mobile_store.dto.response.customer.CustomerResponse;
import com.nguyenkhang.mobile_store.dto.response.payment.PaymentResponse;
import com.nguyenkhang.mobile_store.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponseForCustomer {

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
    CustomerResponse customer;

    List<OrderItemResponse> orderItems;
    PaymentResponse payment;

    List<OrderStatusHistoryResponse> orderStatusHistories;

    LocalDateTime createAt;
}
