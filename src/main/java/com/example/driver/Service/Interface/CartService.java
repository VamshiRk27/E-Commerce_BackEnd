package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Cart.CheckOutCartRequest;
import com.example.driver.DTO.Response.Cart.CartResponse;
import com.example.driver.DTO.Response.Order.OrderResponse;
import com.example.driver.Entity.Item;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CartException;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Exception.ProductException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    CartResponse saveCart(String customerEmail,Item item);
    List<OrderResponse> checkOutCart(CheckOutCartRequest checkOutCartRequest) throws CustomerException, CartException, CardException, ProductException;
}
