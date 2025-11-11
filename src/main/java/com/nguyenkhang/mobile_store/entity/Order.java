package com.nguyenkhang.mobile_store.entity;

import com.nguyenkhang.mobile_store.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
@NamedEntityGraph(name = "Order.withItems",
        attributeNodes = {
                @NamedAttributeNode(value = "orderItems")
        }
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @Column(unique = true, length = 20)
    String orderCode;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderStatusHistory> orderStatusHistories;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    Payment payment;

    @Column(columnDefinition = "VARCHAR(10)")
    String phoneNumber;

    @Column(columnDefinition = "TEXT")
    String shippingAddress;

    @Column(nullable = false, length = 60)
    String province;

    @Column(nullable = false, length = 60)
    String district;

    @Column(nullable = false, length = 60)
    String ward;

    Double deliveryLatitude;
    Double deliveryLongitude;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    Voucher voucher;

    @Enumerated(value = EnumType.STRING)
    OrderStatus status;

    @Column(columnDefinition = "TEXT")
    String note;

    String receiverName;

    double reducedAmount;

    double totalAmount;

    double finalAmount;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "create_by")
    User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    User updateBy;

    @CreationTimestamp
    LocalDateTime createAt;

    @UpdateTimestamp
    LocalDateTime updateAt;
}
