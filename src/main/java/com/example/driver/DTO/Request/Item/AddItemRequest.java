package com.example.driver.DTO.Request.Item;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class AddItemRequest {
    String customerEmail; //This will help to retrieve the customer Cart ID
    Integer requiredQuantity;
    Integer productId;
}
