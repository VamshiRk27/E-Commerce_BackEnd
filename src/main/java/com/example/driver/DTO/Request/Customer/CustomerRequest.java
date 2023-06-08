package com.example.driver.DTO.Request.Customer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class CustomerRequest {
    String name;
    String emailId;
    Integer age;
    String mobileNumber;
    String address;
}
