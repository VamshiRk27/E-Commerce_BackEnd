package com.example.driver.DTO.Response.Customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class CustomerResponse {
    String name;
    String emailId;
    Integer age;
    String address;
}
