package com.example.driver.Controller;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.Service.Interface.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    SellerService sellerService;

    // 1.Seller Register API (Add Seller)
    @PostMapping("/add-seller")
    public ResponseEntity sellerRegister(@RequestBody AddSellerRequest addSellerRequest){
        // Add (or) Register a new Seller in the Database using Seller Request DTO
        // Since the emailId is the unique identifier for a seller check whether a seller exists with the given emailId
        // If a Seller exists with the given emailId return an Exception "A Seller with given emailId already exists"
        // Register a new Seller with given data.
        try{
            AddSellerResponse response=sellerService.sellerRegister(addSellerRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
