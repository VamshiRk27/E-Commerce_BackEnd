package com.example.driver.Tranformer;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.DTO.Response.Seller.SellerResponse;
import com.example.driver.Entity.Seller;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerTransformer {
    public static Seller addSellerRequestToSeller(AddSellerRequest addSellerRequest){
        return Seller.builder()
                .name(addSellerRequest.getName())
                .emailId(addSellerRequest.getEmailId())
                .age(addSellerRequest.getAge())
                .mobileNumber(addSellerRequest.getMobileNumber())
                .address(addSellerRequest.getAddress())
                .build();
    }

    public static AddSellerResponse addSellerResponseFromSeller(Seller seller){
        String sellerName=seller.getName();
        String sellerEmail=seller.getEmailId();
        String outMessage="A new Seller with email "+"'"+sellerEmail+"'"+" has been added";
        return AddSellerResponse.builder()
                .name(sellerName)
                .emailId(sellerEmail)
                .message(outMessage).build();
    }

    public static SellerResponse sellerResponseFromSeller(Seller seller){
        return SellerResponse.builder()
                .name(seller.getName())
                .emailId(seller.getEmailId())
                .age(seller.getAge())
                .address(seller.getAddress())
                .build();
    }
}
