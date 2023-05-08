package com.example.driver.Controller;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Service.Interface.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    // 1.Add Customer
    @PostMapping("/add-customer")
    public ResponseEntity registerCustomer(@RequestBody AddCustomerRequest addCustomerRequest){
        //Register a new Customer into the Database by providing required Credentials
        //Since the EmailId is unique identifier if the email already exists throw an Exception
        //Register the customer and return a Response.
        try {
            AddCustomerResponse response=customerService.registerCustomer(addCustomerRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 2.View All Customers
    @GetMapping("/get-all-customers")
    public ResponseEntity getAllCustomers() {
        //Get all the Customers from the Database in the form of Response List
        try {
            List<CustomerResponse> responseList=customerService.getAllCustomers();
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    // 3.Get a Customer by email/mobile number
    // 4.Get all customers whose age is greater than 'x'
    // 5.Get all customers who use VISA card
    // 6.Update info of the customer
    // 7.delete a customer by email/mobile number
    // 8.get all MasterCard customers whose expiry is less than 6 Months
    // 9.Return the card type which has maximum number of cards , similarly for minimum
}
