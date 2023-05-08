package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Entity.Cart;
import com.example.driver.Entity.Customer;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Service.Interface.CustomerService;
import com.example.driver.Tranformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    // 1.Add Customer
    @Override
    public AddCustomerResponse registerCustomer(AddCustomerRequest addCustomerRequest) throws CustomerException {
        //Check the Database whether a customer is already registered with the given EmailId
        Customer customer=customerRepository.findByEmailId(addCustomerRequest.getEmailId());
        if(customer!=null){
            //If the Customer already exists then throw an Exception
            throw new CustomerException("Customer with given emailId "+"'"+addCustomerRequest.getEmailId()+"'"+" already exists");
        }
        //Prepare the Customer object from the Request DTO
        customer= CustomerTransformer.addCustomerRequestToCustomer(addCustomerRequest);
        Cart cart= Cart.builder().cartTotal(0).numberOfItems(0).customer(customer).build(); //Create a new Cart
        customer.setCart(cart); //Set a newly created Cart for the customer
        customerRepository.save(customer); //Save the Customer object into the Database
        AddCustomerResponse response; //Declaring a new Customer Add Response
        response=CustomerTransformer.addCustomerResponseFromCustomer(customer); //Preparing the response
        return response; //Return the Response
    }

    // 2.Get all Customers
    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers=customerRepository.findAll(); //Get all the customers from the Database
        List<CustomerResponse> responseList=new ArrayList<>(); //Initialising a new ArrayList
        //Prepare Customer Response for each and every Customer
        for(Customer customer:customers){
            //Preparing Customer Response for each Customer
            CustomerResponse response=CustomerTransformer.customerResponseFromCustomer(customer);
            responseList.add(response); //Add the Response to the ResponseList
        }
        return responseList; //Return the Response List
    }
}
