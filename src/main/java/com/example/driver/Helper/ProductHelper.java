package com.example.driver.Helper;

import com.example.driver.Entity.Item;
import com.example.driver.Entity.Orders;
import com.example.driver.Entity.Product;
import com.example.driver.Enum.ProductStatus;
import com.example.driver.Exception.ProductException;

public class ProductHelper {
    public static void decreaseProductQuantity(Orders order) throws ProductException {
        Item item=order.getItem();
        Product product=item.getProduct();
        int orderedQuantity=item.getRequiredQuantity();
        int productQuantity=product.getQuantity();
        if(productQuantity<orderedQuantity){
            throw new ProductException("Available quantity is less than required quantity");
        }
        product.setQuantity(productQuantity-orderedQuantity);
        if(product.getQuantity()==0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
    }
}
