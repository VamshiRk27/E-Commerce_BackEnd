package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Request.Customer.CustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.DTO.Response.Customer.CustomerOperationResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Enum.CardType;
import com.example.driver.Exception.CustomerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    AddCustomerResponse registerCustomer(AddCustomerRequest addCustomerRequest) throws CustomerException;
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByEmail(String customerEmail) throws CustomerException;
    List<CustomerResponse> getCustomersOfAgeGreaterThan(Integer age);
    List<CustomerResponse> getCustomersOfAgeEqualTo(Integer age);
    CustomerOperationResponse updateCustomerInfo(String customerEmail, CustomerRequest customerRequest) throws CustomerException;
    CustomerOperationResponse deleteCustomerByEmail(String customerEmail) throws CustomerException;
    List<CustomerResponse> getCustomersOfAgeLessThan(Integer age);
    List<CustomerResponse> getAllCustomersFromLocation(String address);
}
