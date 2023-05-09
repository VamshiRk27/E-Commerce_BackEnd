package com.example.driver.Service.Interface;

import com.example.driver.DTO.Response.Cart.CartResponse;
import com.example.driver.Entity.Item;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartResponse saveCart(String customerEmail,Item item);
}
