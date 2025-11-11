package com.nguyenkhang.mobile_store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // tao mot builder pattern
@FieldDefaults(level = AccessLevel.PRIVATE)
// tat ca field trong class se mac dinh la private va khong can dung private truoc cac field nua
public class RoleResponse {
    String name;
    String description;
    LocalDate createAt;
}

