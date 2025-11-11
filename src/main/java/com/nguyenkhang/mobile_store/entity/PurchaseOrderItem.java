package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    ProductVariant productVariant;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false,precision = 15,scale = 2)
    BigDecimal pricePerUnit;
    @Column(nullable = false,precision = 15,scale = 2)
    BigDecimal total;
}
