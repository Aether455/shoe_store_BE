package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(columnDefinition = "TEXT")
    String description;


    @ManyToOne
    @JoinColumn(name = "create_by_user_id")
    User createBy;
    @ManyToOne
    @JoinColumn(name = "update_by_user_id")
    User updateBy;


    @CreationTimestamp
    LocalDateTime createAt;

    @UpdateTimestamp
    LocalDateTime updateAt;

}
