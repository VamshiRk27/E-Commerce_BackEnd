package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.Entity.Card;
import com.example.driver.Entity.Customer;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Repository.CardRepository;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Service.Interface.CardService;
import com.example.driver.Tranformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public AddCardResponse addCard(AddCardRequest addCardRequest) throws CustomerException, CardException {
        String email= addCardRequest.getCustomerEmailId();
        Customer customer=customerRepository.findByEmailId(email);
        if(customer==null){
            throw new CustomerException("Sorry! Customer with given email "+"'"+email+"'"+" doesn't exist");
        }
        Card card=cardRepository.findByCardNumber(addCardRequest.getCardNumber());
        if(card!=null){
            throw new CardException("Card with given card number already exists");
        }
        List<Card> customerCards=customer.getCards();
        card=CardTransformer.addCardRequestToCard(addCardRequest);
        customerCards.add(card);
        card.setCustomer(customer);
        customerRepository.save(customer);

        AddCardResponse response=CardTransformer.addCardResponseFromCard(card);
        return response;
    }
}
