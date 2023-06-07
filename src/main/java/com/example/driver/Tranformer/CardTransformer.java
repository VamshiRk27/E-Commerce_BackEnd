package com.example.driver.Tranformer;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.DTO.Response.Card.CardResponse;
import com.example.driver.DTO.Response.Card.CustomerCardResponse;
import com.example.driver.Entity.Card;
import com.example.driver.Entity.Customer;
import com.example.driver.Helper.CardHelper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardTransformer {
    public static Card addCardRequestToCard(AddCardRequest addCardRequest){
        return Card.builder()
                .cardNumber(addCardRequest.getCardNumber())
                .cardType(addCardRequest.getCardType())
                .cvv(addCardRequest.getCvv())
                .expiryDate(CardHelper.addExpiry(addCardRequest.getExpiryYears()))
                .build();
    }
    public static AddCardResponse addCardResponseFromCard(Card card){
        String customerName=card.getCustomer().getName();
        String outMessage="A new Card for customer "+"'"+customerName+"'"+" has been added";
        return AddCardResponse.builder()
                .cardNumber(card.getCardNumber())
                .customerName(customerName)
                .message(outMessage).build();
    }
    public static CustomerCardResponse customerCardResponseFromCard(Card card){
        return CustomerCardResponse.builder()
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .expiryDate(card.getExpiryDate()).build();
    }
    public static CardResponse cardResponseFromCard(Card card){
        return CardResponse.builder()
                .cardNumber(card.getCardNumber())
                .customerName(card.getCustomer().getName())
                .expiryDate(card.getExpiryDate()).build();
    }
}
