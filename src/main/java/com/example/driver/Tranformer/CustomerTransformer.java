package com.example.driver.Tranformer;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Entity.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerTransformer {
    public static Customer addCustomerRequestToCustomer(AddCustomerRequest addCustomerRequest){
        return Customer.builder()
                .name(addCustomerRequest.getName())
                .age(addCustomerRequest.getAge())
                .emailId(addCustomerRequest.getEmailId())
                .address(addCustomerRequest.getAddress())
                .mobileNumber(addCustomerRequest.getMobileNumber())
                .build();
    }
    public static AddCustomerResponse addCustomerResponseFromCustomer(Customer customer) {
        String customerName= customer.getName();
        String customerEmail= customer.getEmailId();
        String outMessage="A new Customer "+"'"+customerName+"'"+" with emailId "+"'"+customerEmail+"'"+" has been Registered.";
        return AddCustomerResponse.builder()
                .name(customerName)
                .emailId(customerEmail)
                .message(outMessage)
                .build();
    }
    public static CustomerResponse customerResponseFromCustomer(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .emailId(customer.getEmailId())
                .age(customer.getAge())
                .address(customer.getAddress())
                .build();
    }
}
