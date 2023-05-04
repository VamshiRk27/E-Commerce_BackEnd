package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.DTO.Response.Seller.SellerOperationResponse;
import com.example.driver.DTO.Response.Seller.SellerResponse;
import com.example.driver.Entity.Seller;
import com.example.driver.Exception.SellerException.SellerException;
import com.example.driver.Helper.SellerHelper;
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

    // 5.Update Seller info Based on Email
    @Override
    public SellerOperationResponse updateSellerInfo(String sellerEmail, SellerRequest sellerRequest) throws SellerException {
        Seller seller=sellerRepository.findByEmailId(sellerEmail); //Retrieve the Seller using given emailId
        if(seller==null){
            // If Seller doesn't exist then throw a SellerException
            throw new SellerException("Seller with given email "+sellerEmail+" doesn't exist");
        }
        Seller updatedSeller=SellerHelper.updateSeller(seller,sellerRequest); //Update the Seller data using Helper
        sellerRepository.save(updatedSeller); //Save the updated Seller
        SellerOperationResponse response=new SellerOperationResponse(); //Initializing an Operation Response
        response.setMessage("Your Seller profile data has been Updated."); //Setting the operation message
        return response; //Returning the Response
    }

    // 6.Delete Seller by Email
    @Override
    public SellerOperationResponse deleteSellerByEmail(String sellerEmail) throws SellerException {
        //Retrieve Seller by Email Address
        Seller seller=sellerRepository.findByEmailId(sellerEmail);
        if(seller==null){
            //If seller doesn't exist then throw an Exception
            throw new SellerException("Seller with "+sellerEmail+" doesn't exist");
        }
        sellerRepository.delete(seller); //Delete seller from the Database
        SellerOperationResponse response=new SellerOperationResponse(); //Initialising a new Seller Operation Response
        response.setMessage("Seller with email Id "+sellerEmail+" has been deleted."); //Setting the Operation message
        return response; //Returning the Response
    }
}
