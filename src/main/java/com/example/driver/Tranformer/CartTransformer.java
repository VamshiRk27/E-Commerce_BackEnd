package com.example.driver.Tranformer;

import com.example.driver.DTO.Response.Cart.CartResponse;
import com.example.driver.Entity.Cart;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartTransformer {
    public static CartResponse cartResponseFromCart(Cart cart){
        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .customerName(cart.getCustomer().getName())
                .numberOfItems(cart.getNumberOfItems())
                .build();
    }
}
