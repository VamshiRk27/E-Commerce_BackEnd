package com.example.driver.DTO.Response.Product;

import com.example.driver.Enum.ProductCategory;
import com.example.driver.Enum.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder

public class ProductResponse {
    String name;
    Integer price;
    ProductCategory productCategory;
    ProductStatus productStatus;
}
