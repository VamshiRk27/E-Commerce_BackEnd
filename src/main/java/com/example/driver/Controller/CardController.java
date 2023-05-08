package com.example.driver.Controller;

import com.example.driver.DTO.Request.Card.AddCardRequest;
import com.example.driver.DTO.Response.Card.AddCardResponse;
import com.example.driver.Service.Interface.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;

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
}