package com.example.driver.Service.Interface;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.DTO.Response.Seller.SellerOperationResponse;
import com.example.driver.DTO.Response.Seller.SellerResponse;
import com.example.driver.Exception.SellerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {
    AddSellerResponse sellerRegister(AddSellerRequest addSellerRequest) throws SellerException;
    SellerResponse getSellerByEmailId(String sellerEmail) throws SellerException;
    SellerResponse getSellerBySellerId(Integer sellerId) throws SellerException;
    List<SellerResponse> getAllSellers();
    SellerOperationResponse updateSellerInfo(String sellerEmail,SellerRequest sellerRequest) throws SellerException;
    SellerOperationResponse deleteSellerByEmail(String sellerEmail) throws SellerException;
    SellerOperationResponse deleteSellerById(Integer sellerId) throws SellerException;
    List<SellerResponse> getAllSellersByAge(Integer age);
    List<SellerResponse> getAllSellersOfAgeLessThan(Integer age);
}
