package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CustomerException;
import org.springframework.stereotype.Service;

@Service
public interface CardService {
    AddCardResponse addCard(AddCardRequest addCardRequest) throws CustomerException, CardException;
}
