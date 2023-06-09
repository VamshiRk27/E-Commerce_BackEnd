package com.example.driver.Controller;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.DTO.Response.Seller.SellerOperationResponse;
import com.example.driver.DTO.Response.Seller.SellerResponse;
import com.example.driver.Service.Interface.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 2.Get Seller by EmailId
    @GetMapping("/get-seller-by-emailId/{emailId}")
    public ResponseEntity getSellerByEmailId(@PathVariable("emailId") String sellerEmail){
        // Get the Seller details from the Database using Unique identifier value EmailId
        // If the Seller with emailID doesn't exist return an Exception "No Seller exists with given EmailID"
        try {
            SellerResponse response=sellerService.getSellerByEmailId(sellerEmail);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 3.Get Seller By SellerID
    @GetMapping("/get-seller-by-seller-id")
    public ResponseEntity getSellerBySellerId(@RequestParam("sellerId") int sellerId){
        // Get the Seller details from the Database using Seller ID
        // If the Seller with given SellerID doesn't exist return an Exception "No Seller exists with given ID"
        try{
            SellerResponse response=sellerService.getSellerBySellerId(sellerId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 4.Get All Sellers
    @GetMapping("/get-all-sellers")
    public ResponseEntity getAllSellers(){
        // Get all the Seller Details in the form of a list using Response Entity
        try{
            List<SellerResponse> responseList=sellerService.getAllSellers();
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 5.Update Seller info Based on Email
    @PutMapping("/update-seller-info")
    public ResponseEntity updateSellerInfo(@RequestParam("emailId") String sellerEmail, @RequestBody SellerRequest sellerRequest){
        //Update the Seller data using the given sellerId as Identifier
        //If the Seller doesn't exist with given emailId then throw an exception "Seller with given email 'sellerEmail' doesn't exist"
        try{
            SellerOperationResponse response=sellerService.updateSellerInfo(sellerEmail,sellerRequest);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 6.Delete a Seller by Email
    @DeleteMapping("/delete-seller-by-email")
    public ResponseEntity deleteSellerByEmail(@RequestParam("emailId") String sellerEmail){
        //Delete the Seller using the unique identifier key Seller EmailId
        //If the Seller with given email doesn't exist throw an exception "Seller with 'sellerEmail' doesn't exist"
        try{
            SellerOperationResponse response=sellerService.deleteSellerByEmail(sellerEmail);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 7.Delete Seller by Id
    @DeleteMapping("/delete-seller-by-id/{sellerId}")
    public ResponseEntity deleteSellerById(@PathVariable("sellerId") Integer sellerId){
        //Delete the Seller from the Database using SellerId
        //If the seller with SellerId doesn't exist then throw an exception
        try{
            SellerOperationResponse response=sellerService.deleteSellerById(sellerId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 8.Get all Sellers of a Particular age
    @GetMapping("/get-all-sellers-of-age/{age}")
    public ResponseEntity getAllSellersByAge(@PathVariable("age") Integer age){
        //Get all the Sellers of a given age in the form of Response Data
        try{
            List<SellerResponse> responseList=sellerService.getAllSellersByAge(age);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 9.Get all Sellers of age less than the given Age
    @GetMapping("/get-all-sellers-of-age-less-than/{age}")
    public ResponseEntity getAllSellersOfAgeLessThan(@PathVariable("age") Integer age){
        //Get all the Sellers of age less than given age in the form of Response Data
        try{
            List<SellerResponse> responseList=sellerService.getAllSellersOfAgeLessThan(age);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
