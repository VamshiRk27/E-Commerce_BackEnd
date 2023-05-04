package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.Entity.Seller;
import com.example.driver.Exception.SellerException.SellerException;
import com.example.driver.Repository.SellerRepository;
import com.example.driver.Service.Interface.SellerService;
import com.example.driver.Tranformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Override
    public AddSellerResponse sellerRegister(AddSellerRequest addSellerRequest) throws SellerException {
        //Check if there exists any Seller with the given EmailID
        Seller seller=sellerRepository.findByEmailId(addSellerRequest.getEmailId());
        if(seller!=null){
            //If the result obtained is not null then there exists a Seller with the given EmailID
            throw new SellerException("A Seller with given emailId already exists");
        }

        seller= SellerTransformer.addSellerRequestToSeller(addSellerRequest); //Transforming Seller Request to Seller
        Seller savedSeller=sellerRepository.save(seller); //Saves Seller to the Database
        AddSellerResponse response; //Declaring a AddSellerResponse
        response=SellerTransformer.addSellerResponseFromSeller(savedSeller); //Preparing a Response from Seller Object
        // returning the Response
        return response;
    }
}
