package com.example.driver.Controller;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.DTO.Response.Product.ProductResponse;
import com.example.driver.Service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 2.Get all Products of a Category
    @GetMapping("/get-all-products-of-category")
    public ResponseEntity getAllProductsByCategory(@RequestParam("category") String category){
        //Get all the products of a given category and return the List of products in the form of Product Response
        try{
            List<ProductResponse> responseList=productService.getAllProductsByCategory(category);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 3.Get all Products by seller EmailID
    @GetMapping("/get-all-products-by-seller-email/{email}")
    public ResponseEntity getProductsBySeller(@PathVariable("email") String sellerEmail){
        try{
            List<ProductResponse> responseList=productService.getProductsBySeller(sellerEmail);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
