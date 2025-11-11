package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.request.order.PaymentRequest;
import com.nguyenkhang.mobile_store.dto.response.payment.PaymentResponse;
import com.nguyenkhang.mobile_store.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "status",constant = "PENDING")
    Payment toPayment(PaymentRequest request);

    PaymentResponse toPaymentResponse(Payment payment);
}
