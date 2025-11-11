package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Cart;
import com.nguyenkhang.mobile_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(long userId);
}
