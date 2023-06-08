package com.example.driver.Helper;

import com.example.driver.Entity.Item;
import com.example.driver.Entity.Product;

public class ItemHelper {
    public static void setItemProduct(Item item,Product product){
        item.setName(product.getName()); //Setting the Item name using Product name
        item.setProduct(product); //Mapping the Product for Item object
        product.getItemList().add(item);
    }
}
