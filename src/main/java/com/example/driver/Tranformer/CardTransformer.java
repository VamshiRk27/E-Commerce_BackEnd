package com.example.driver.Tranformer;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.Entity.Card;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardTransformer {
    public static Card addCardRequestToCard(AddCardRequest addCardRequest){
        return Card.builder()
                .cardNumber(addCardRequest.getCardNumber())
                .cardType(addCardRequest.getCardType())
                .cvv(addCardRequest.getCvv())
                .expiryDate(addCardRequest.getExpiryDate())
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
}
