package com.example.driver.DTO.Response.Seller;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class SellerResponse {
    String name;
    String emailId;
    Integer age;
    String address;
}
