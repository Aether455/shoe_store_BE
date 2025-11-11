package com.nguyenkhang.mobile_store.controller;

import com.nguyenkhang.mobile_store.dto.ApiResponse;
import com.nguyenkhang.mobile_store.dto.request.CartItemRequest;
import com.nguyenkhang.mobile_store.dto.request.CartItemUpdateQuantityRequest;
import com.nguyenkhang.mobile_store.dto.response.cart.CartItemResponse;
import com.nguyenkhang.mobile_store.dto.response.cart.CartResponse;
import com.nguyenkhang.mobile_store.service.CartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @PostMapping
    ApiResponse<CartItemResponse> addToCart(@RequestBody @Valid CartItemRequest request){
        return ApiResponse.<CartItemResponse>builder().message("Success!").result(cartService.addToCart(request)).build();
    }

    @GetMapping("/me")
    ApiResponse<CartResponse> getMyCart(){
        return ApiResponse.<CartResponse>builder().message("Success!").result(cartService.getMyCart()).build();
    }

    @PutMapping("/quantity")
    ApiResponse<CartItemResponse> updateQuantity(@RequestBody @Valid CartItemUpdateQuantityRequest request){
        return  ApiResponse.<CartItemResponse>builder().message("Success!").result(cartService.updateQuantity(request)).build();
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    ApiResponse<String> deleteCartItem(@PathVariable long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return ApiResponse.<String>builder().message("Success!").result("Cart item has been deleted").build();
    }

    @DeleteMapping("/clear")
    ApiResponse<String> clearMyCart(){
        cartService.clearMyCart();
        return ApiResponse.<String>builder().message("Success!").result("Cart has been deleted").build();
    }
}
