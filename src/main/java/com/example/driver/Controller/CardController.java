package com.example.driver.Controller;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.DTO.Response.Card.CardResponse;
import com.example.driver.DTO.Response.Card.CustomerCardResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Enum.CardType;
import com.example.driver.Service.Interface.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;

    // 1.Add Card
    @PostMapping("/add-card")
    public ResponseEntity addCard(@RequestBody AddCardRequest addCardRequest){
        // Add card for a Customer
        // If Customer doesn't exist throw an Exception
        // If a Card with given card number exists already then throw an Exception
        try {
            AddCardResponse response=cardService.addCard(addCardRequest);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 2.Get all Cards and Customer Name with given Card Type
    @GetMapping("/get-all-cards-of-a-customer/{customerId}")
    public ResponseEntity getAllCardsOfCustomer(@PathVariable("customerId") Integer customerId){
        // Get all Cards of a Customer in the form of Customer_Card_Response
        // If Customer doesn't exist throw an Exception
        // If Customer doesn't have a card linked return null
        try {
            List<CustomerCardResponse> responseList=cardService.getAllCardsOfCustomer(customerId);
            return new ResponseEntity<>(responseList,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 3.Get all Customers of given Card Type
    @GetMapping("/get-all-customers-of-card-type/{cardType}")
    public ResponseEntity getAllCustomersOfCardType(@PathVariable("cardType") CardType cardType) {
        // Get the list of Customers in the form of Customer response who use provided Card_Type
        try {
            List<CustomerResponse> responseList=cardService.getAllCustomersOfCardType(cardType);
            return new ResponseEntity<>(responseList,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 4.get all Cards whose Expiry Date is after the given date
    @GetMapping("/get-all-cards-after-expiry/{date}")
    public ResponseEntity getAllCardsAfterExpiry(@PathVariable("date") Date date){
        // Get all Cards from the Database whose Expiry Date is after the given Date
        try {
            List<CardResponse> responseList=cardService.getAllCardsAfterExpiry(date);
            return new ResponseEntity<>(responseList,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 5.get all Cards whose Expiry Date is before the given date
    @GetMapping("/get-all-cards-before-expiry/{date}")
    public ResponseEntity getAllCardsBeforeExpiry(@PathVariable("date") Date date){
        // Get all Cards from the Database whose Expiry Date is before the given Date
        try {
            List<CardResponse> responseList=cardService.getAllCardsBeforeExpiry(date);
            return new ResponseEntity<>(responseList,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 6.get all cards of a Card Type whose Expiry Date is after than given date
    @GetMapping("/get-all-card_type-cards-after-expiry")
    public ResponseEntity getAllCardsAfterExpiryOfCardType(@RequestParam("cardType") CardType cardType,@RequestParam("date") Date date){
        // Get all Cards of a Card Type from the Database whose Expiry Date is after the given Date
        try {
            List<CardResponse> responseList=cardService.getAllCardTypeCardsAfterExpiry(cardType,date);
            return new ResponseEntity<>(responseList,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 7.get all cards of a Card Type whose Expiry Date is before the given date
    @GetMapping("/get-all-card_type-cards-before-expiry")
    public ResponseEntity getAllCardsBeforeExpiryOfCardType(@RequestParam("cardType") CardType cardType,@RequestParam("date") Date date){
        // Get all Cards of a Card Type from the Database whose Expiry Date is before the given Date
        try {
            List<CardResponse> responseList=cardService.getAllCardTypeCardsBeforeExpiry(cardType,date);
            return new ResponseEntity<>(responseList,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 8.Return the CardType which has maximum number of that CARD
    @GetMapping("/get-the-card_type-having-maximum-customers")
    public ResponseEntity getCardTypeHavingMaximumCustomers(){
        // Get the Card Type which has maximum number of Customers
        try {
            CardType response=cardService.getCardTypeHavingMaximumCustomers();
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 9.Return the CardType which has minimum number of that CARD
    @GetMapping("/get-the-card_type-having-minimum-customers")
    public ResponseEntity getCardTypeHavingMinimumCustomers(){
        // Get the Card Type which has minimum number of Customers
        try {
            CardType response=cardService.getCardTypeHavingMinimumCustomers();
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 10.get all given Card Type customers whose expiry is less than 'x' Months
    @GetMapping("/get-all-customers-using-card_type-having-expiry-less-than/{months}")
    public ResponseEntity getAllCustomersUsingCardHavingExpiryLessThanMonths(
            @PathVariable("months") Integer months,
            @RequestParam("cardType")CardType cardType) {
        // Get all the Customers whose given CardType has validity less than 'x' months
        try {
            List<CustomerResponse> responseList = cardService.getAllCustomersUsingCardHavingExpiryLessThanMonths(months, cardType);
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 11.get all given Card Type customers whose expiry is less than 'x' Days
    @GetMapping("/get-all-customers-using-card_type-having-expiry-less-than/{days}")
    public ResponseEntity getAllCustomersUsingCardHavingExpiryLessThanDays(
            @PathVariable("days") Integer days,
            @RequestParam("cardType")CardType cardType) {
        // Get all the Customers whose given CardType has validity less than 'x' Days
        try {
            List<CustomerResponse> responseList = cardService.getAllCustomersUsingCardHavingExpiryLessThanDays(days, cardType);
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}