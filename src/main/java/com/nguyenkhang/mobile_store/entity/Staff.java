package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    User user;

    @Column(nullable = false)
    String fullName;

    @Column(columnDefinition = "VARCHAR(10)",unique = true)
    String phoneNumber;

    String position;

    LocalDateTime hireDate;

    double salary;

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
