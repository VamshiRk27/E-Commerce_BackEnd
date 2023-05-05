package com.example.driver.Controller;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    // 1.Add Product
    @PostMapping("/add-product")
    public ResponseEntity addProduct(@RequestBody AddProductRequest addProductRequest){
        //Add Product by Seller using unique identifier Seller Email from Request DTO
        //If the Seller doesn't exist throw a Seller Exception
        //After successful addition of Product return the Product Response with appropriate message
        try{
            AddProductResponse response=productService.addProduct(addProductRequest);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
