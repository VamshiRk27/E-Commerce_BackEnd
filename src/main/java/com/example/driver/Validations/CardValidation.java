package com.example.driver.Validations;

import com.example.driver.Entity.Card;
import com.example.driver.Exception.CardException;

public class CardValidation {
    public static void cardValidation(Card card) throws CardException {
        if(card!=null){
            throw new CardException("Card with given card number already exists");
        }
    }
}
