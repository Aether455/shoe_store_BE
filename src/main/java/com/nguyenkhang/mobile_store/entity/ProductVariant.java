package com.nguyenkhang.mobile_store.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "signature"}))
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    String sku;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(columnDefinition = "TEXT")
    String productVariantImageUrl;

    String imagePublicId;

    Double price;

    @ManyToMany
    @JoinTable(
            name = "product_variant_option_values",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "option_value_id"))
    Set<OptionValue> optionValues;

    @Column(columnDefinition = "int default 0")
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

    @Column(nullable = false)
    String signature;

    @Version
    @Column(columnDefinition = "bigint default 0")
    Long version = 0L;
}
