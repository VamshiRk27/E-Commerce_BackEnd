package com.example.driver.Validations;

import com.example.driver.Entity.Card;
import com.example.driver.Entity.Customer;
import com.example.driver.Exception.CardException;

public class CardValidation {
    public static void cardValidation(Card card) throws CardException {
        if(card!=null){
            throw new CardException("Card with given card number already exists");
        }
    }
    public static void noCardValidation(Card card) throws CardException {
        if(card==null){
            //If card is null throw an Exception
            throw new CardException("Card not found");
        }
    }
    public static void cardOwnerValidation(Card card,Customer customer) throws CardException {
        if(card.getCustomer()!=customer){
            //If the given Customer and customer of card are different then, throw an Exception
            throw new CardException("Card doesn't belong to Customer");
        }
    }
    public static void cardCVVValidation(Integer cardCvv,Integer inputCVV) throws CardException {
        if(!cardCvv.equals(inputCVV)){
            //If Card Cvv and provided cvv are different then throw an Exception
            throw new CardException("Credentials mismatch\nCannot authenticate Card");
        }
    }
}
