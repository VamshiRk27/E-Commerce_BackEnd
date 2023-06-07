package com.example.driver.Controller;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.DTO.Response.Card.CustomerCardResponse;
import com.example.driver.DTO.Response.Customer.CustomerResponse;
import com.example.driver.Enum.CardType;
import com.example.driver.Service.Interface.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;

    // 1.Add Card
    @PostMapping("/add-card")
    public ResponseEntity addCard(@RequestBody AddCardRequest addCardRequest){
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
        try {
            List<CustomerResponse> responseList=cardService.getAllCustomersOfCardType(cardType);
            return new ResponseEntity<>(responseList,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 3.get all Cards whose Expiry Date is greater than given date
    // 4.get all Cards whose Expiry Date is less than given date
    // 5.get all MASTERCard whose Expiry Date is less than given date
    // 6.get all MASTERCard whose Expiry Date is greater than given date
    // 7.Return the CardType which has maximum number of that CARD
}