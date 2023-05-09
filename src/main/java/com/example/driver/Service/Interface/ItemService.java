package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.Entity.Item;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Exception.ProductException;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    //We add Item to the Cart so the return type becomes Item Entity
    Item addItem(AddItemRequest addItemRequest) throws CustomerException, ProductException;
}
