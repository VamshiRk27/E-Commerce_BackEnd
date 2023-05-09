package com.example.driver.DTO.Response.Item;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class ItemResponse {
    String productName;
    Integer quantity;
    Integer eachItemPrice;
    Integer totalPrice;
}
