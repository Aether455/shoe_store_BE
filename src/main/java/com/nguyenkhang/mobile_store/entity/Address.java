package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
     Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(columnDefinition = "TEXT")
    String address;

    @Column(nullable = false, length = 60)
    String province;

    @Column(nullable = false, length = 60)
    String district;

    @Column(nullable = false, length = 60)
    String ward;

    @CreationTimestamp
    LocalDateTime createAt;


}
