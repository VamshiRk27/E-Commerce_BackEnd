package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.DTO.Response.Card.CustomerCardResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Enum.CardType;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CustomerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardService {
    AddCardResponse addCard(AddCardRequest addCardRequest) throws CustomerException, CardException;
    List<CustomerCardResponse> getAllCardsOfCustomer(Integer customerId) throws CustomerException;
    List<CustomerResponse> getAllCustomersOfCardType(CardType cardType);
}
