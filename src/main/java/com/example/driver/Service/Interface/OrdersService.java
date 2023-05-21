package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.DTO.Response.Order.OrderResponse;
import com.example.driver.Entity.Card;
import com.example.driver.Entity.Customer;
import com.example.driver.Entity.Item;
import com.example.driver.Entity.Orders;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Exception.ProductException;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {
    Orders placeOrderFromCart(Customer customer,Card card,Item item) throws ProductException;
    OrderResponse placeDirectOrder(AddItemRequest addItemRequest,String cardNumber,Integer cvv) throws CustomerException, ProductException, CardException;
}
