package com.nguyenkhang.mobile_store.entity;

import com.nguyenkhang.mobile_store.enums.PaymentMethod;
import com.nguyenkhang.mobile_store.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @OneToOne
    @JoinColumn(name = "order_id",nullable = false)
    Order order;

    double amount;

    @Enumerated(value = EnumType.STRING)
    PaymentMethod method;

    @Enumerated(value = EnumType.STRING)
    PaymentStatus status ;

    @CreationTimestamp
    LocalDateTime createAt;

    @UpdateTimestamp
    LocalDateTime updateAt;
}
