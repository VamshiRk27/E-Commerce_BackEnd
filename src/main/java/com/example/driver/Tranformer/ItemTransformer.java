package com.example.driver.Tranformer;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.DTO.Response.Item.ItemResponse;
import com.example.driver.Entity.Item;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemTransformer {
    public static Item addItemRequestToItem(AddItemRequest addItemRequest){
        return Item.builder()
                .requiredQuantity(addItemRequest.getRequiredQuantity()).build();
    }
    public static ItemResponse itemResponseFromItem(Item item){
        return ItemResponse.builder()
                .eachItemPrice(item.getProduct().getPrice())
                .totalPrice(item.getRequiredQuantity()*item.getProduct().getPrice())
                .quantity(item.getRequiredQuantity())
                .productName(item.getProduct().getName())
                .build();
    }
}
