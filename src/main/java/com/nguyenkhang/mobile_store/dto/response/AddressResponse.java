package com.nguyenkhang.mobile_store.dto.response;

import com.nguyenkhang.mobile_store.entity.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddressResponse {

    Long id;

    String address;

    String province;

    String district;

    String ward;

    LocalDateTime createAt;


}
