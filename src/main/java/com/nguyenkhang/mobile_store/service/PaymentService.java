package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.order.PaymentUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.payment.PaymentResponse;
import com.nguyenkhang.mobile_store.entity.Payment;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.PaymentMapper;
import com.nguyenkhang.mobile_store.repository.OrderRepository;
import com.nguyenkhang.mobile_store.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;

    public PaymentResponse updatePaymentStatus(long id, PaymentUpdateRequest request){
        Payment payment = paymentRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));
        payment.setStatus(request.getStatus());

        return paymentMapper.toPaymentResponse(payment);
    }
}
