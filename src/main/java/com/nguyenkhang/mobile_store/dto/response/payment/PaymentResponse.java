package com.nguyenkhang.mobile_store.dto.response.payment;

import com.nguyenkhang.mobile_store.entity.Order;
import com.nguyenkhang.mobile_store.enums.PaymentMethod;
import com.nguyenkhang.mobile_store.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    Long id;

    double amount;

    PaymentMethod method;

    PaymentStatus status;

    LocalDateTime createAt;

}
