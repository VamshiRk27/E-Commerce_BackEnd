package com.example.driver.DTO.Request.Product;

import com.example.driver.Enum.ProductCategory;
import com.example.driver.Enum.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class AddProductRequest {
    String sellerEmail;
    String name;
    Integer price;
    Integer quantity;
    ProductCategory productCategory;
    ProductStatus productStatus;
}
