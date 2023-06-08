package com.example.driver.Validations;

import com.example.driver.Entity.Customer;
import com.example.driver.Exception.CustomerException;

public class CustomerValidation {
    public static void noCustomerEmailValidation(Customer customer,String customerEmail) throws CustomerException {
        if(customer==null){
            throw new CustomerException("Sorry! Customer with given email "+"'"+customerEmail+"'"+" doesn't exist");
        }
    }
    public static void noCustomerIDValidation(Customer customer,Integer customerId) throws CustomerException {
        if(customer==null){
            throw new CustomerException("Sorry! Customer with given id "+"'"+customerId+"'"+" doesn't exist");
        }
    }
    public static void customerEmailValidation(Customer customer,String customerEmail) throws CustomerException {
        if(customer!=null){
            throw new CustomerException("Customer with given email "+"'"+customerEmail+"'"+" already exist");
        }
    }
}
