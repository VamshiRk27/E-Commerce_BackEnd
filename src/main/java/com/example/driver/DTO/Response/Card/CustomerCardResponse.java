package com.example.driver.DTO.Response.Card;

import com.example.driver.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class CustomerCardResponse {
    String cardNumber;
    CardType cardType;
    Date expiryDate;
}
