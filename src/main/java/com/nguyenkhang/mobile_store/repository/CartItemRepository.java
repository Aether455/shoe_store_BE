package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Cart;
import com.nguyenkhang.mobile_store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository  extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartIdAndProductVariantId(long cartId, long productVariantId);
    void deleteAllByCartId(long cartId);
}
