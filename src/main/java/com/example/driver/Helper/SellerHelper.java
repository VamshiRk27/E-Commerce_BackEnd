package com.example.driver.Helper;

import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.Entity.Seller;

public class SellerHelper {
    public static Seller updateSeller(Seller seller, SellerRequest sellerRequest){
        if(sellerRequest.getName()!=null){
            seller.setName(sellerRequest.getName());
        }
        if(sellerRequest.getAge()!=null){
            seller.setAge(sellerRequest.getAge());
        }
        if(sellerRequest.getEmailId()!=null){
            seller.setEmailId(sellerRequest.getEmailId());
        }
        if(sellerRequest.getMobileNumber()!=null){
            seller.setMobileNumber(sellerRequest.getMobileNumber());
        }
        return seller;
    }
}
