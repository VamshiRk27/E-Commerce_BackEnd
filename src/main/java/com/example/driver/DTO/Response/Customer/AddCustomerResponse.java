package com.example.driver.DTO.Response.Customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class AddCustomerResponse {
    String name;
    String emailId;
    String message;
}
