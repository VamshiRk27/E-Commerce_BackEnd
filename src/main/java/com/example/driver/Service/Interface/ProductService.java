package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.Exception.SellerException.SellerException;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    AddProductResponse addProduct(AddProductRequest addProductRequest) throws SellerException;
}
