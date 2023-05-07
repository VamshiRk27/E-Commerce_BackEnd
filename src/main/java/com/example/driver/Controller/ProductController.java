package com.example.driver.Controller;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.DTO.Response.Product.ProductOperationResponse;
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
        //Get all the Products sold by a Seller using Seller EmailId
        //If the seller doesn't exist then throw an Exception
        try{
            List<ProductResponse> responseList=productService.getProductsBySeller(sellerEmail);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 4.Delete a Product by sellerEmail and ProductId
    @DeleteMapping("/delete-a-product-of-seller")
    public ResponseEntity deleteASellerProduct(@RequestParam("emailId") String sellerEmail,@RequestParam("productId") Integer productId){
        //Delete the product with given product id sold by given Seller
        //If the Seller doesn't exist then throw an Exception
        //If the Product doesn't exist then throw an Exception
        //Delete the product and send a message as Response
        try{
            ProductOperationResponse response=productService.deleteASellerProduct(sellerEmail,productId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 5.Get Top 5 Cheapest products
    @GetMapping("/get-top-five-cheapest-products")
    public ResponseEntity getTopFiveCheapestProducts(){
        //Get the top 5 Cheapest products from the Database
        //Return the products as List of Product Response
        try{
            List<ProductResponse> responseList=productService.getTopFiveCheapestProducts();
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    // 6.Get Top 5 Costliest Products
    @GetMapping("/get-top-five-costliest-products")
    public ResponseEntity getTopFiveCostliestProducts(){
        //Get the top 5 Costliest Products Available
        //Return the products as List of Product Response
        try{
            List<ProductResponse> responseList=productService.getTopFiveCostliestProducts();
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
}
