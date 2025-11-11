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
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(columnDefinition = "TEXT")
    String address;
    @Column(columnDefinition = "VARCHAR(20)",unique = true)
    String phoneNumber;
    @Column(unique = true,nullable = false)
    String email;

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
