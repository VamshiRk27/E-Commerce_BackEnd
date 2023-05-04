package com.example.driver.DTO.Response.Seller;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class AddSellerResponse {
    String name;
    String emailId;
    String message;
}
