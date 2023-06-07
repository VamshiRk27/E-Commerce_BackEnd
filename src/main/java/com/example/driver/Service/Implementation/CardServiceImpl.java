package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.DTO.Response.Card.CustomerCardResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Entity.Card;
import com.example.driver.Entity.Customer;
import com.example.driver.Enum.CardType;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Repository.CardRepository;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Service.Interface.CardService;
import com.example.driver.Tranformer.CardTransformer;
import com.example.driver.Tranformer.CustomerTransformer;
import com.example.driver.Validations.CardValidation;
import com.example.driver.Validations.CustomerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CustomerRepository customerRepository;


    // 1.Add Card
    @Override
    public AddCardResponse addCard(AddCardRequest addCardRequest) throws CustomerException, CardException {
        String email= addCardRequest.getCustomerEmailId(); // Customer Email-ID
        Customer customer=customerRepository.findByEmailId(email);// Retrieving Customer from Database using EmailID
        CustomerValidation.noCustomerEmailValidation(customer,email);// If Customer doesn't exist throws an Exception
        Card card=cardRepository.findByCardNumber(addCardRequest.getCardNumber());
        CardValidation.cardValidation(card); // If Card exists with given Card Number throws an Exception
        List<Card> customerCards=customer.getCards(); // Retrieve Customers cards
        card=CardTransformer.addCardRequestToCard(addCardRequest); //Prepare Card from Card Request
        customerCards.add(card); // Add Card to Customer's Card's List
        card.setCustomer(customer); // Set the Customer for the newly created Card
        customerRepository.save(customer); // Save the Parent Entity(Customer) Database

        AddCardResponse response=CardTransformer.addCardResponseFromCard(card);
        return response;
    }


    // 2.Get all Cards of a Customer
    @Override
    public List<CustomerCardResponse> getAllCardsOfCustomer(Integer customerId) throws CustomerException {
        Customer customer=customerRepository.findById(customerId).get();
        CustomerValidation.noCustomerIDValidation(customer,customerId);
        List<Card> cards=customer.getCards();
        List<CustomerCardResponse> responseList=new ArrayList<>();
        for(Card card : cards){
            CustomerCardResponse response=CardTransformer.customerCardResponseFromCard(card);
            responseList.add(response);
        }
        return responseList;
    }

    // 3.Get all Customers of given Card Type
    public List<CustomerResponse> getAllCustomersOfCardType(CardType cardType){
        List<Customer> customers=customerRepository.customersByCardType(cardType);
        List<CustomerResponse> responseList=new ArrayList<>();
        for(Customer customer:customers){
            CustomerResponse response= CustomerTransformer.customerResponseFromCustomer(customer);
            responseList.add(response);
        }
        return responseList;
    }
}
