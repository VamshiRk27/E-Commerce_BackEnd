package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.Entity.Cart;
import com.example.driver.Entity.Customer;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Service.Interface.CustomerService;
import com.example.driver.Tranformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    // 1.Add Customer
    @Override
    public AddCustomerResponse registerCustomer(AddCustomerRequest addCustomerRequest) throws CustomerException {
        Customer customer=customerRepository.findByEmailId(addCustomerRequest.getEmailId());
        if(customer!=null){
            throw new CustomerException("Customer with given emailId "+"'"+addCustomerRequest.getEmailId()+"'"+" already exists");
        }
        customer= CustomerTransformer.addCustomerRequestToCustomer(addCustomerRequest);
        Cart cart= Cart.builder().cartTotal(0).numberOfItems(0).customer(customer).build();
        customer.setCart(cart);
        customerRepository.save(customer);
        AddCustomerResponse response;
        response=CustomerTransformer.addCustomerResponseFromCustomer(customer);
        return response;
    }
}
