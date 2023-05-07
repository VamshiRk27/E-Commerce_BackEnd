package com.example.driver.Controller;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.Service.Interface.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    // 1.Add Customer
    @PostMapping("/add-customer")
    public ResponseEntity registerCustomer(@RequestBody AddCustomerRequest addCustomerRequest){
        try {
            AddCustomerResponse response=customerService.registerCustomer(addCustomerRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
