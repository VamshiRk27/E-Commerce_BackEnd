package com.example.driver.Helper;

import com.example.driver.Entity.Cart;
import com.example.driver.Entity.Customer;

import java.util.ArrayList;

public class CartHelper {
    public static void resetCart(Cart cart){
        cart.setCartTotal(0);
        cart.setNumberOfItems(0);
        cart.setItems(new ArrayList<>());
    }
    public static void createCart(Customer customer){
        Cart cart=Cart.builder().cartTotal(0).numberOfItems(0).customer(customer).build(); // Create a new CArt
        customer.setCart(cart); //Set a newly created Cart for the customer
    }
}
