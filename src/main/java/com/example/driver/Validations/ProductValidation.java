package com.example.driver.Validations;

import com.example.driver.Entity.Product;
import com.example.driver.Exception.ProductException;

public class ProductValidation {
    public static void noProductWithIdValidation(Product product,Integer productId) throws ProductException {
        if(product==null){
            //If the Product is null then throw an Exception
            throw new ProductException("Product with given id "+productId+" doesn't exist");
        }
    }
}
