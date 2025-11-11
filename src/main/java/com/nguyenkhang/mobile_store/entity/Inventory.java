package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
        "warehouse_id","product_variant_id"
}))
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    ProductVariant productVariant;

    @Column(nullable = false)
    int quantity;
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
    @Version
    Long version;
}
