package com.example.driver.Helper;

import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.Entity.Seller;

public class SellerHelper {
    public static String changesMessage(Seller seller,SellerRequest sellerRequest){
        String changes="",message="";
        if(!sellerRequest.getName().equals(seller.getName())){
            message="Seller name updated to "+"'"+sellerRequest.getName()+"'"+" from "+seller.getName();
            changes+=message+"\n";
        }
        if(!sellerRequest.getEmailId().equals(seller.getEmailId())){
            message="Seller email updated to "+"'"+sellerRequest.getEmailId()+"'"+" from "+seller.getEmailId();
            changes+=message+"\n";
        }
        if(!sellerRequest.getAge().equals(seller.getAge())){
            message="Seller age updated to "+"'"+sellerRequest.getAge()+"'"+" from "+seller.getAge();
            changes+=message+"\n";
        }
        if(!sellerRequest.getMobileNumber().equals(seller.getMobileNumber())){
            message="Seller mobile number updated to "+"'"+sellerRequest.getMobileNumber()+"'"+" from "+seller.getMobileNumber();
            changes+=message+"\n";
        }
        if(!sellerRequest.getAddress().equals(seller.getAddress())){
            message="Seller address updated to "+"'"+sellerRequest.getAddress()+"'"+" from "+seller.getAddress();
            changes+=message+"\n";
        }
        return changes;
    }
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
        if(sellerRequest.getAddress()!=null){
            seller.setAddress(sellerRequest.getAddress());
        }
        return seller;
    }
}
