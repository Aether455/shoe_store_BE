package com.nguyenkhang.mobile_store.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.nguyenkhang.mobile_store.enums.PurchaseOrderStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PurchaseOrderItem> purchaseOrderItems;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    PurchaseOrderStatus status = PurchaseOrderStatus.DRAFT;

    @Column(nullable = false, precision = 15, scale = 2)
    BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "create_by")
    User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    User updateBy;

    @UpdateTimestamp
    LocalDateTime updateAt;

    @CreationTimestamp
    LocalDateTime createAt;

    @Version
    @Column(columnDefinition = "bigint default 0")
    Long version = 0L;
}
