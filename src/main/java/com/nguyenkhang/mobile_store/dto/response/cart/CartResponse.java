package com.nguyenkhang.mobile_store.dto.response.cart;

import com.nguyenkhang.mobile_store.entity.CartItem;
import com.nguyenkhang.mobile_store.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {

    Long id;

    List<CartItemResponse> cartItems;

    double totalAmount;

    LocalDateTime createAt;
}
