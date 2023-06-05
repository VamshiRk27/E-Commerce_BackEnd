package com.example.driver.Validations;

import com.example.driver.Entity.Seller;
import com.example.driver.Exception.SellerException;

public class SellerValidation {
    public static void noSellerEmailValidation(Seller seller,String sellerEmail) throws SellerException {
        if(seller==null){
            //If the result obtained is null then there exists no Seller with given EmailID
            String message="Seller with email "+"'"+sellerEmail+"'"+" doesn't exist";
            throw new SellerException(message);
        }
    }
    public static void sellerEmailValidation(Seller seller) throws SellerException {
        if(seller!=null){
            //If the result obtained is not null then there exists a Seller with the given EmailID
            String message="A Seller with emailId "+"'"+seller.getEmailId()+"'"+" already exists";
            throw new SellerException(message);
        }
    }
    public static void noSellerIdValidation(Seller seller,Integer sellerId) throws SellerException {
        if(seller==null){
            //If the result obtained is null then there exists no Seller with given SellerID
            String message="No Seller exists with given Seller Id "+"'"+sellerId+"'";
            throw new SellerException(message);
        }
    }
}
