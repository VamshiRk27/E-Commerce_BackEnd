package com.example.driver.Helper;

import com.example.driver.Entity.Cart;

import java.util.ArrayList;

public class CartHelper {
    public static void resetCart(Cart cart){
        cart.setCartTotal(0);
        cart.setNumberOfItems(0);
        cart.setItems(new ArrayList<>());
    }
}
