package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Customer.AddCustomerRequest;
import com.example.driver.DTO.Request.Customer.CustomerRequest;
import com.example.driver.DTO.Response.Customer.AddCustomerResponse;
import com.example.driver.DTO.Response.Customer.CustomerOperationResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.DTO.Response.Seller.SellerOperationResponse;
import com.example.driver.Entity.Card;
import com.example.driver.Entity.Cart;
import com.example.driver.Entity.Customer;
import com.example.driver.Entity.Seller;
import com.example.driver.Enum.CardType;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Helper.CartHelper;
import com.example.driver.Helper.CustomerHelper;
import com.example.driver.Helper.SellerHelper;
import com.example.driver.Repository.CardRepository;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Service.Interface.CustomerService;
import com.example.driver.Tranformer.CustomerTransformer;
import com.example.driver.Validations.CustomerValidation;
import com.example.driver.Validations.SellerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;

    // 1.Add Customer
    @Override
    public AddCustomerResponse registerCustomer(AddCustomerRequest addCustomerRequest) throws CustomerException {
        //Check the Database whether a customer is already registered with the given EmailId
        Customer customer=customerRepository.findByEmailId(addCustomerRequest.getEmailId());
        // If the Customer with the given Email exists throw an Exception
        CustomerValidation.customerEmailValidation(customer,addCustomerRequest.getEmailId());
        //Prepare the Customer object from the Request DTO
        customer= CustomerTransformer.addCustomerRequestToCustomer(addCustomerRequest);
        CartHelper.createCart(customer); //Create a new Cart for Newly created Customer
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

    // 3.Get a Customer by email/mobile number
    @Override
    public CustomerResponse getCustomerByEmail(String customerEmail) throws CustomerException {
        //Get the Customer from the Database using customer EmailId
        Customer customer=customerRepository.findByEmailId(customerEmail);
        // Validation for Customer, if doesn't exist throw an Exception
        CustomerValidation.noCustomerEmailValidation(customer,customerEmail);
        //Prepare a Customer Response for the retrieved customer
        CustomerResponse response=CustomerTransformer.customerResponseFromCustomer(customer);
        return response; //Returning the CustomerResponse
    }

    // 4.Get all customers whose age is greater than 'x'
    @Override
    public List<CustomerResponse> getCustomersOfAgeGreaterThan(Integer age) {
        //Get all the Customers from the Database matching the given criteria
        List<Customer> customersList=customerRepository.customersGreaterThanAge(age);
        List<CustomerResponse> responseList=new ArrayList<>(); //Initialise a new ArrayList
        //Prepare a Customer Response for each and every Customer
        for(Customer customer:customersList){
            //Prepare Customer Response for each customer object
            CustomerResponse response=CustomerTransformer.customerResponseFromCustomer(customer);
            responseList.add(response); //Add the response to the ResponseList
        }
        return responseList; //Return the ResponseList
    }

    // 5.Get all customers whose age equal to 'x'
    @Override
    public List<CustomerResponse> getCustomersOfAgeEqualTo(Integer age) {
        //Get all the Customers from the Database matching the given criteria
        List<Customer> customersList=customerRepository.customersOfGivenAge(age);
        List<CustomerResponse> responseList=new ArrayList<>(); //Initialise a new ArrayList
        //Prepare a Customer Response for each and every Customer
        for(Customer customer:customersList){
            //Prepare Customer Response for each customer object
            CustomerResponse response=CustomerTransformer.customerResponseFromCustomer(customer);
            responseList.add(response); //Add the response to the ResponseList
        }
        return responseList; //Return the ResponseList
    }

    // 6.Update info of the customer
    @Override
    public CustomerOperationResponse updateCustomerInfo(String customerEmail, CustomerRequest customerRequest) throws CustomerException {
        Customer customer=customerRepository.findByEmailId(customerEmail); //Retrieve the Customer using given emailId
        CustomerValidation.noCustomerEmailValidation(customer,customerEmail); //Validates the Customer if it doesn't exist throws an Exception
        CustomerOperationResponse response=new CustomerOperationResponse(); //Initializing an Operation Response
        response.setChanges(CustomerHelper.changesMessage(customer,customerRequest)); // Setting changes message for Response

        Customer updatedCustomer=CustomerHelper.updateCustomer(customer,customerRequest); //Update the Customer data using Helper
        customerRepository.save(updatedCustomer); //Save the updated Customer
        response.setMessage("Customer profile data has been Updated."); //Setting the operation message
        return response; //Returning the Response
    }

    // 7.delete a customer by email
    @Override
    public CustomerOperationResponse deleteCustomerByEmail(String customerEmail) throws CustomerException {
        //Retrieve Customer by Email Address
        Customer customer=customerRepository.findByEmailId(customerEmail);
        CustomerValidation.noCustomerEmailValidation(customer,customerEmail);
        customerRepository.delete(customer); //Delete Customer from the Database
        CustomerOperationResponse response=new CustomerOperationResponse(); //Initialising a new Customer Operation Response
        response.setMessage("Customer with email Id "+customerEmail+" has been deleted."); //Setting the Operation message
        return response; //Returning the Response
    }

    // 8.Get all customers whose age is less than 'x'
    @Override
    public List<CustomerResponse> getCustomersOfAgeLessThan(Integer age) {
        //Get all the Customers from the Database matching the given criteria
        List<Customer> customersList=customerRepository.customersLessThanAge(age);
        List<CustomerResponse> responseList=new ArrayList<>(); //Initialise a new ArrayList
        //Prepare a Customer Response for each and every Customer
        for(Customer customer:customersList){
            //Prepare Customer Response for each customer object
            CustomerResponse response=CustomerTransformer.customerResponseFromCustomer(customer);
            responseList.add(response); //Add the response to the ResponseList
        }
        return responseList; //Return the ResponseList
    }

    // 9.Get all customers from Location 'x'
    @Override
    public List<CustomerResponse> getAllCustomersFromLocation(String address) {
        // Get all Customers from given Location
        List<Customer> customers=customerRepository.customersFromLocation(address);
        List<CustomerResponse> responseList=new ArrayList<>(); //Initialise a new ArrayList

        for(Customer customer : customers) {
            //For each Customer prepare a CustomerResponse
            CustomerResponse response=CustomerTransformer.customerResponseFromCustomer(customer);
            responseList.add(response); //Add the response to ResponseList
        }
        return responseList; //return the ResponseList
    }

}
