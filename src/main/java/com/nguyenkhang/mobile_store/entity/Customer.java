package com.nguyenkhang.mobile_store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    String fullName;

    @Column(columnDefinition = "VARCHAR(10)",unique = true)
    String phoneNumber;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Address> addresses;

    @ManyToOne
    @JoinColumn(name = "create_by")
    @JsonIgnore

    User createBy;
    @ManyToOne
    @JoinColumn(name = "update_by")
    @JsonIgnore
    User updateBy;

    @CreationTimestamp
    LocalDateTime createAt;

    @UpdateTimestamp
    LocalDateTime updateAt;


    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    User user;

}
