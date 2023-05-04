package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.DTO.Response.Seller.SellerResponse;
import com.example.driver.Entity.Seller;
import com.example.driver.Exception.SellerException.SellerException;
import com.example.driver.Repository.SellerRepository;
import com.example.driver.Service.Interface.SellerService;
import com.example.driver.Tranformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;

    // 1.Register Seller
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

    // 2.Get Seller by EmailId
    @Override
    public SellerResponse getSellerByEmailId(String sellerEmail) throws SellerException {
        //Check if there exists any Seller with the given EmailID
        Seller seller=sellerRepository.findByEmailId(sellerEmail);
        if(seller==null){
            //If the result obtained is null then there exists no Seller with given EmailID
            throw new SellerException("No Seller exists with given EmailID");
        }

        SellerResponse response; //Declaring a SellerResponse
        response=SellerTransformer.sellerResponseFromSeller(seller); //Preparing a Response from Seller Object
        return response; //Returning the Response
    }

    // 3.Get Seller by SellerId
    @Override
    public SellerResponse getSellerBySellerId(Integer sellerId) throws SellerException {
        //Check if there exists any Seller with the given EmailID
        Seller seller=sellerRepository.findById(sellerId).get();
        if(seller==null){
            //If the result obtained is null then there exists no Seller with given EmailID
            throw new SellerException("No Seller exists with given ID"); //Exception message is different in PostMan
        }

        SellerResponse response; //Declaring a SellerResponse
        response=SellerTransformer.sellerResponseFromSeller(seller); //Preparing a Response from Seller Object
        return response; //Returning the Response
    }

    // 4.Get All Sellers
    @Override
    public List<SellerResponse> getAllSellers() {
        List<Seller> sellersList=sellerRepository.findAll(); //Retrieve all Seller's from the Database
        List<SellerResponse> responseList=new ArrayList<>(); // Initialising a new ArrayList for Seller Response

        //Loop for preparing SellerResponse for each existing Seller
        for(Seller seller:sellersList){
            SellerResponse response=SellerTransformer.sellerResponseFromSeller(seller); //Preparing the Response for each Seller
            responseList.add(response); //Adding the Response to the ResponseList
        }
        return responseList; //Returning the ResponseList
    }
}
