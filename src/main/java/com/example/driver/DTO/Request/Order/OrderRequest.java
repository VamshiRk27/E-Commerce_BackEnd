package com.example.driver.DTO.Request.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class OrderRequest {
    Integer customerId;
    String cardNumber;
    Integer itemId;
}
