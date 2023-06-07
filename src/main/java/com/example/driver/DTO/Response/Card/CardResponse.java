package com.example.driver.DTO.Response.Card;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class CardResponse {
    String cardNumber;
    String customerName;
    Date expiryDate;
}
