package com.example.driver.Helper;

import com.example.driver.DTO.Request.Customer.CustomerRequest;
import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.Entity.Customer;
import com.example.driver.Entity.Seller;

public class CustomerHelper {
    public static String changesMessage(Customer customer, CustomerRequest customerRequest){
        String changes="",message="";
        if(!customerRequest.getName().equals(customer.getName())){
            message="Seller name updated to "+"'"+customerRequest.getName()+"'"+" from "+customer.getName();
            changes+=message+"\n";
        }
        if(!customerRequest.getEmailId().equals(customer.getEmailId())){
            message="Seller email updated to "+"'"+customerRequest.getEmailId()+"'"+" from "+customer.getEmailId();
            changes+=message+"\n";
        }
        if(!customerRequest.getAge().equals(customer.getAge())){
            message="Seller age updated to "+"'"+customerRequest.getAge()+"'"+" from "+customer.getAge();
            changes+=message+"\n";
        }
        if(!customerRequest.getMobileNumber().equals(customer.getMobileNumber())){
            message="Seller mobile number updated to "+"'"+customerRequest.getMobileNumber()+"'"+" from "+customer.getMobileNumber();
            changes+=message+"\n";
        }
        if(!customerRequest.getAddress().equals(customer.getAddress())){
            message="Seller address updated to "+"'"+customerRequest.getAddress()+"'"+" from "+customer.getAddress();
            changes+=message+"\n";
        }
        return changes;
    }

    public static Customer updateCustomer(Customer customer, CustomerRequest customerRequest){
        if(customerRequest.getName()!=null){
            customer.setName(customerRequest.getName());
        }
        if(customerRequest.getAge()!=null){
            customer.setAge(customerRequest.getAge());
        }
        if(customerRequest.getEmailId()!=null){
            customer.setEmailId(customerRequest.getEmailId());
        }
        if(customerRequest.getMobileNumber()!=null){
            customer.setMobileNumber(customerRequest.getMobileNumber());
        }
        if(customerRequest.getAddress()!=null){
            customer.setAddress(customerRequest.getAddress());
        }
        return customer;
    }
}
