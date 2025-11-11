package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    String oldStatus;
    String newStatus;

    @ManyToOne
    @JoinColumn(name = "change_by")
    User changeBy;

    @CreationTimestamp
    LocalDateTime changeAt;

}
