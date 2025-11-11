package com.nguyenkhang.mobile_store.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @Column(nullable = false)
    String name;

    String description;

    @CreationTimestamp
    LocalDateTime createAt;
    @UpdateTimestamp
    LocalDateTime updateAt;

}
