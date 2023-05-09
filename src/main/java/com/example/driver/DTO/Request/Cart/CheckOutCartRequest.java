package com.example.driver.DTO.Request.Cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class CheckOutCartRequest {
    String customerEmail;
    String cardNo;
    Integer cvv;
}
