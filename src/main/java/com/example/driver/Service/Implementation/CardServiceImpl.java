package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.DTO.Response.Card.CardResponse;
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

import java.sql.Date;
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
        Customer customer=customerRepository.findById(customerId).get();// Retrieve Customer from Database
        // If Customer doesn't exist then throw an Exception
        CustomerValidation.noCustomerIDValidation(customer,customerId);
        //If Customer exists then retrieve all his cards.
        List<Card> cards=customer.getCards();
        //If Customer doesn't have any cards linked to his account return null
        if(cards==null){
            return null;
        }
        List<CustomerCardResponse> responseList=new ArrayList<>(); // Response List for Customer cards
        for(Card card : cards){
            // For each card prepare Customer card response
            CustomerCardResponse response=CardTransformer.customerCardResponseFromCard(card);
            responseList.add(response); //Add Response to ResponseList
        }
        return responseList; //Return Response
    }

    // 3.Get all Customers of given Card Type
    public List<CustomerResponse> getAllCustomersOfCardType(CardType cardType){
        // Retrieve all Customers of given Card Type from Database
        List<Customer> customers=customerRepository.customersByCardType(cardType);
        List<CustomerResponse> responseList=new ArrayList<>();// Create a new CustomerResponse ArrayList
        for(Customer customer:customers){
            // For each Customer create a new Customer Response
            CustomerResponse response= CustomerTransformer.customerResponseFromCustomer(customer);
            responseList.add(response); // Add the response to the ResponseList
        }
        return responseList; //Return ResponseList
    }

    // 4.get all Cards whose Expiry Date is after the given date
    @Override
    public List<CardResponse> getAllCardsAfterExpiry(Date date) {
        List<Card> cards=cardRepository.findAll(); // Retrieve all Cards from the Database
        List<CardResponse> responseList=new ArrayList<>(); // A new ArrayList for CardResponse List
        for(Card card:cards) {
            // If value returned = 0 , both dates are equal
            // If value returned > 0 , date1 is after date2
            // If value returned < 0 , date1 is before date2
            if(card.getExpiryDate().compareTo(date)>0){
                // Preparing Card Response for each Card item
                CardResponse response=CardTransformer.cardResponseFromCard(card);
                responseList.add(response); // Add the response to ResponseList
            }
        }
        return responseList; // return the Response List
    }

    // 5.get all Cards whose Expiry Date is before the given date
    @Override
    public List<CardResponse> getAllCardsBeforeExpiry(Date date) {
        List<Card> cards=cardRepository.findAll(); // Retrieve all Cards from the Database
        List<CardResponse> responseList=new ArrayList<>(); // A new ArrayList for CardResponse List
        for(Card card:cards) {
            // If value returned = 0 , both dates are equal
            // If value returned > 0 , date1 is after date2
            // If value returned < 0 , date1 is before date2
            if(card.getExpiryDate().compareTo(date)<0){
                // Preparing Card Response for each Card item
                CardResponse response=CardTransformer.cardResponseFromCard(card);
                responseList.add(response); // Add the response to ResponseList
            }
        }
        return responseList; // return the Response List
    }

    // 6.get all cards of a Card Type whose Expiry Date is after than given date
    @Override
    public List<CardResponse> getAllCardTypeCardsAfterExpiry(CardType cardType, Date date) {
        List<Card> cards=cardRepository.cardsByCardType(cardType); // Retrieve all Cards of Card Type from the Database
        List<CardResponse> responseList=new ArrayList<>(); // A new ArrayList for CardResponse List
        for(Card card:cards) {
            // If value returned = 0 , both dates are equal
            // If value returned > 0 , date1 is after date2
            // If value returned < 0 , date1 is before date2
            if(card.getExpiryDate().compareTo(date)>0){
                // Preparing Card Response for each Card item
                CardResponse response=CardTransformer.cardResponseFromCard(card);
                responseList.add(response); // Add the response to ResponseList
            }
        }
        return responseList; // return the Response List
    }

    // 7.get all cards of a Card Type whose Expiry Date is before the given date
    @Override
    public List<CardResponse> getAllCardTypeCardsBeforeExpiry(CardType cardType, Date date) {
        List<Card> cards=cardRepository.cardsByCardType(cardType); // Retrieve all Cards of Card Type from the Database
        List<CardResponse> responseList=new ArrayList<>(); // A new ArrayList for CardResponse List
        for(Card card:cards) {
            // If value returned = 0 , both dates are equal
            // If value returned > 0 , date1 is after date2
            // If value returned < 0 , date1 is before date2
            if(card.getExpiryDate().compareTo(date)<0){
                // Preparing Card Response for each Card item
                CardResponse response=CardTransformer.cardResponseFromCard(card);
                responseList.add(response); // Add the response to ResponseList
            }
        }
        return responseList; // return the Response List
    }

    // 8.Return the CardType which has maximum number of that CARD
    @Override
    public CardType getCardTypeHavingMaximumCustomers() {
        // Retrieve List of Cards of different Card Type from the Database
        // Since we have 3 different Card Types we will have 3 Lists
        List<Card> masterCard=cardRepository.cardsByCardType(CardType.MASTERCARD);
        List<Card> visa=cardRepository.cardsByCardType(CardType.VISA);
        List<Card> rupay=cardRepository.cardsByCardType(CardType.RUPAY);
        long secondMax; // A variable to store the size of second-Largest Card used by Customers
        Enum secondMaxType,maxType; // Enum to store the type of the second-Largest & Largest Card
        // Comparison between two types of Cards
        if(masterCard.size()> visa.size()){
            secondMaxType=CardType.MASTERCARD;
            secondMax=masterCard.size();
        }
        else{
            secondMaxType=CardType.VISA;
            secondMax=visa.size();
        }
        // One of the two values is stored in secondMax & secondMaxType
        
        // Comparison between secondMax and last type of Card
        if(secondMax>rupay.size()){
            maxType=secondMaxType;
        }
        else{
            maxType=CardType.RUPAY;
        }
        // The Type of Card having maximum customers will be stored in maxType Enum.
        return (CardType)maxType; //Return the Card Type
    }

    // 9.Return the CardType which has minimum number of that CARD
    @Override
    public CardType getCardTypeHavingMinimumCustomers() {
        // Retrieve List of Cards of different Card Type from the Database
        // Since we have 3 different Card Types we will have 3 Lists
        List<Card> masterCard=cardRepository.cardsByCardType(CardType.MASTERCARD);
        List<Card> visa=cardRepository.cardsByCardType(CardType.VISA);
        List<Card> rupay=cardRepository.cardsByCardType(CardType.RUPAY);
        long secondMin; // A variable to store the size of second-Lowest Card used by Customers
        Enum secondMinType,minType; // Enum to store the type of the second-Lowest & Lowest Card
        // Comparison between two types of Cards
        if(masterCard.size()<visa.size()){
            secondMinType=CardType.MASTERCARD;
            secondMin=masterCard.size();
        }
        else{
            secondMinType=CardType.VISA;
            secondMin=visa.size();
        }
        // One of the two values is stored in secondMin & secondMinType

        // Comparison between secondMin and last type of Card
        if(secondMin<rupay.size()){
            minType=secondMinType;
        }
        else{
            minType=CardType.RUPAY;
        }
        // The Type of Card having minimum customers will be stored in maxType Enum.
        return (CardType)minType; //Return the Card Type
    }

}
