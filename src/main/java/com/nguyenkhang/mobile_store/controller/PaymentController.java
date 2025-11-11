package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.order.PaymentUpdateRequest;
import com.nguyenkhang.mobile_store.dto.response.payment.PaymentResponse;
import com.nguyenkhang.mobile_store.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    @PutMapping("/{id}")
    public ApiResponse<PaymentResponse> updateStatus(@PathVariable long id, @RequestBody @Valid PaymentUpdateRequest request){
        return ApiResponse.<PaymentResponse>builder().result(paymentService.updatePaymentStatus(id, request)).build();
    }
}
