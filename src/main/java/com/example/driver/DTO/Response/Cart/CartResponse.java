package com.example.driver.DTO.Response.Cart;

import com.example.driver.DTO.Response.Item.ItemResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class CartResponse {
    Integer cartTotal;
    Integer numberOfItems;
    String customerName;
    List<ItemResponse> items;
}
