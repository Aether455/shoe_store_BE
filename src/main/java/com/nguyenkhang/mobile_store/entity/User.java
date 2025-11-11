package com.nguyenkhang.mobile_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @Column(name = "username",unique = true,columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci",nullable = false)
    String username;
    String password;
    @Column(unique = true,nullable = false)
    String email;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Staff staff;


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Customer customer;

    @CreationTimestamp
    LocalDateTime createAt;

    @UpdateTimestamp
    LocalDateTime updateAt;
    @ManyToMany
    Set<Role> roles;

}
