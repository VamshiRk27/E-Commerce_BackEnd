package com.example.driver.Controller;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Request.Customer.CustomerRequest;
import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.DTO.Response.Customer.CustomerOperationResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.DTO.Response.Seller.SellerOperationResponse;
import com.example.driver.Enum.CardType;
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

    // 3.Get a Customer by email
    @GetMapping("/get-customer-by-email")
    public ResponseEntity getCustomerByEmail(@RequestParam("emailId") String customerEmail){
        //Get Customer using Unique Identifier 'EmailId'
        //If the Customer with given email doesn't exist throw an Exception
        try {
            CustomerResponse response=customerService.getCustomerByEmail(customerEmail);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 4.Get all customers whose age is greater than 'x'
    @GetMapping("get-customers-of-age-greater-than/{age}")
    public ResponseEntity getCustomersOfAgeGreaterThan(@PathVariable("age") Integer age) {
        //Get all the Customers having age greater than the given Age in the form of Response List
        try {
            List<CustomerResponse> responseList=customerService.getCustomersOfAgeGreaterThan(age);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 5.Get all customers whose age equal to 'x'
    @GetMapping("get-customers-of-age-equal-to/{age}")
    public ResponseEntity getCustomersOfAgeEqualTo(@PathVariable("age") Integer age) {
        //Get all the Customers having age equal to the given Age in the form of Response List
        try {
            List<CustomerResponse> responseList=customerService.getCustomersOfAgeEqualTo(age);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 8.Get all customers whose age is less than 'x'
    @GetMapping("get-customers-of-age-less-than/{age}")
    public ResponseEntity getCustomersOfAgeLessThan(@PathVariable("age") Integer age) {
        //Get all the Customers having age less than the given Age in the form of Response List
        try {
            List<CustomerResponse> responseList=customerService.getCustomersOfAgeLessThan(age);
            return new ResponseEntity<>(responseList,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 6.Update info of the customer
    @PutMapping("/update-customer-info")
    public ResponseEntity updateCustomerInfo(@RequestParam("emailId") String customerEmail, @RequestBody CustomerRequest customerRequest){
        //Update the Customer data using the given sellerId as Identifier
        //If the Customer doesn't exist with given emailId then throw an exception "Customer with given email 'customerEmail' doesn't exist"
        try{
            CustomerOperationResponse response=customerService.updateCustomerInfo(customerEmail,customerRequest);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 7.delete a customer by email
    @DeleteMapping("/delete-customer-by-email")
    public ResponseEntity deleteCustomerByEmail(@RequestParam("emailId") String customerEmail){
        //Delete the Customer using the unique identifier key Customer EmailId
        //If the Customer with given email doesn't exist throw an exception "Customer with 'customerEmail' doesn't exist"
        try{
            CustomerOperationResponse response=customerService.deleteCustomerByEmail(customerEmail);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 9.Get all customers from Location 'x'
    @GetMapping("/get-all-customers-from-address/")
    public ResponseEntity getAllCustomersFromLocation(@RequestParam("address") String address){
        try{
            List<CustomerResponse> response=customerService.getAllCustomersFromLocation(address);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
