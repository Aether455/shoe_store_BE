package com.nguyenkhang.mobile_store.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.nguyenkhang.mobile_store.enums.InventoryReferenceType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;


    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    ProductVariant productVariant;

    @Enumerated(value = EnumType.STRING)
    InventoryReferenceType type;

    @Column(nullable = false)
    int quantityChange;

    @Column(nullable = false)
    Long referenceId;

    @Column(columnDefinition = "TEXT")
    String note;

    @ManyToOne
    @JoinColumn(name = "create_by")
    User createBy;

    @CreationTimestamp
    LocalDateTime createAt;
}
