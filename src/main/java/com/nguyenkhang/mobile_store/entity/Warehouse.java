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
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @Column(nullable = false)
    String name;
    @Column(columnDefinition = "TEXT")
    String address;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(nullable = false,unique = true)
    int priority;

    @Column(nullable = false, length = 60)
    String province;

    @Column(nullable = false, length = 60)
    String district;

    @Column(nullable = false, length = 60)
    String ward;

    Double latitude;
    Double longitude;

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
