package com.example.driver.Controller;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.DTO.Response.Cart.CartResponse;
import com.example.driver.Entity.Item;
import com.example.driver.Service.Interface.CartService;
import com.example.driver.Service.Interface.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ItemService itemService;
    @Autowired
    CartService cartService;

    // 1.Add item to Cart
    @PostMapping("/add-item-to-cart")
    public ResponseEntity addToCart(@RequestBody AddItemRequest addItemRequest){
        //Add Item to the item Repository
        //Add the saved item into Customer cart and return the cart info in the form of Response Entity
        //If the Customer doesn't exist throw an Exception
        //If the Product doesn't exist or available quantity is short of required quantity then throw an Exception
        try{
            Item savedItem=itemService.addItem(addItemRequest); //Getting the saved item from the Database
            //Adding the saved item to the Customer Cart
            CartResponse response=cartService.saveCart(addItemRequest.getCustomerEmail(),savedItem);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
