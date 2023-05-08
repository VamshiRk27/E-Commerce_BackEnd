package com.example.driver.DTO.Request.Card;

import com.example.driver.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class AddCardRequest {
    String customerEmailId;
    String cardNumber;
    Integer cvv;
    CardType cardType;
    Date expiryDate;
}
