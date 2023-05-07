package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.Exception.CustomerException;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    AddCustomerResponse registerCustomer(AddCustomerRequest addCustomerRequest) throws CustomerException;
}
