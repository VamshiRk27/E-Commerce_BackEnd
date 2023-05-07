package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.DTO.Response.Product.ProductOperationResponse;
import com.example.driver.DTO.Response.Product.ProductResponse;
import com.example.driver.Exception.ProductException.ProductException;
import com.example.driver.Exception.SellerException.SellerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    AddProductResponse addProduct(AddProductRequest addProductRequest) throws SellerException;
    List<ProductResponse> getAllProductsByCategory(String category);
    List<ProductResponse> getProductsBySeller(String sellerEmail) throws SellerException;
    ProductOperationResponse deleteASellerProduct(String sellerEmail,Integer productId) throws SellerException, ProductException;
}
