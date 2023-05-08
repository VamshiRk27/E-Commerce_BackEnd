package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Exception.CustomerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    AddCustomerResponse registerCustomer(AddCustomerRequest addCustomerRequest) throws CustomerException;
    List<CustomerResponse> getAllCustomers();
}
