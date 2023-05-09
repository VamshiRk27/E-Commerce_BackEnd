package com.example.driver.Service.Interface;

import com.example.driver.Entity.Card;
import com.example.driver.Entity.Customer;
import com.example.driver.Entity.Item;
import com.example.driver.Entity.Orders;
import com.example.driver.Exception.ProductException;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {
    Orders placeOrderFromCart(Customer customer,Card card,Item item) throws ProductException;
}
