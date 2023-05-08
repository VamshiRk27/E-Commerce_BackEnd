package com.example.driver.DTO.Response.Card;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class AddCardResponse {
    String cardNumber;
    String customerName;
    String message;
}
