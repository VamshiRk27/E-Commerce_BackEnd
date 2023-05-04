package com.example.driver.DTO.Request.Seller;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class AddSellerRequest {
    String name;
    String emailId;
    Integer age;
    String mobileNumber;
}
