package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.response.cart.CartItemResponse;
import com.nguyenkhang.mobile_store.dto.response.cart.CartResponse;
import com.nguyenkhang.mobile_store.entity.Cart;
import com.nguyenkhang.mobile_store.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toCartResponse(Cart cart);

    List<CartItemResponse> toListCartItemResponse(List<CartItem> cartItems);

    @Mapping(target = "productId",source = "product.id")
    @Mapping(target = "productName",source = "product.name")
    @Mapping(target = "productVariantId",source = "productVariant.id")
    @Mapping(target = "sku",source = "productVariant.sku")
    @Mapping(target= "optionValues", source = "productVariant.optionValues")
    @Mapping(target = "productImageUrl", source = "productVariant.productVariantImageUrl")
    @Mapping(target = "price", source = "productVariant.price")
    @Mapping(target = "totalPrice",expression = "java(cartItem.getQuantity() * cartItem.getProductVariant().getPrice())")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
