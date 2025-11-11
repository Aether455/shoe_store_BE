package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.request.CartItemRequest;
import com.nguyenkhang.mobile_store.dto.request.CartItemUpdateQuantityRequest;
import com.nguyenkhang.mobile_store.dto.response.cart.CartItemResponse;
import com.nguyenkhang.mobile_store.dto.response.cart.CartResponse;
import com.nguyenkhang.mobile_store.entity.*;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import com.nguyenkhang.mobile_store.mapper.CartMapper;
import com.nguyenkhang.mobile_store.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    ProductVariantRepository productVariantRepository;
    UserRepository userRepository;
    CartMapper cartMapper;

    UserService userService;

    public CartItemResponse addToCart(CartItemRequest cartItemRequest) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var cart = cartRepository.findByUserId(user.getId()).orElseGet(()->cartRepository.save(Cart.builder().user(user).build()));

        Product product = productRepository.findById(cartItemRequest.getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        ProductVariant productVariant = productVariantRepository.findById(cartItemRequest.getProductVariantId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_EXISTED));


        var cartItemExisting = cartItemRepository.findByCartIdAndProductVariantId(cart.getId(),productVariant.getId());

        CartItem cartItem;

        if (cartItemExisting.isPresent()) {
            cartItem = cartItemExisting.get();
            cartItem.setQuantity(cartItemExisting.get().getQuantity() + cartItemRequest.getQuantity());
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .productVariant(productVariant)
                    .product(product)
                    .quantity(cartItemRequest.getQuantity())
                    .build();
        }

        cartItem = cartItemRepository.save(cartItem);

        return cartMapper.toCartItemResponse(cartItem);
    }

    public CartResponse getMyCart(){

        User user = userService.getCurrentUser();

        var cart = cartRepository.findByUserId(user.getId()).orElseGet(()->cartRepository.save(Cart.builder().user(user).build()));

        return cartMapper.toCartResponse(cart);
    }

    public CartItemResponse updateQuantity(CartItemUpdateQuantityRequest request){
        User user =userService.getCurrentUser();

        var cart = cartRepository.findByUserId(user.getId()).orElseGet(()->cartRepository.save(Cart.builder().user(user).build()));

        var cartItem = cartItemRepository.findByCartIdAndProductVariantId(cart.getId(),request.getProductVariantId())
                .orElseThrow(()->new AppException(ErrorCode.CART_ITEM_NOT_EXIST));

        cartItem.setQuantity(request.getQuantity());

        cartItem = cartItemRepository.save(cartItem);


        return cartMapper.toCartItemResponse(cartItem);
    }

    public void deleteCartItem(long cartItemId){
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearMyCart(){
        try{
            User user = userService.getCurrentUser();

            var cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXIST));

            cartItemRepository.deleteAllByCartId(cart.getId());
        } catch (Exception e) {
            throw new AppException(ErrorCode.CART_CLEAR_ERROR);
        }
    }

}
