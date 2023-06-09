package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Seller.AddSellerRequest;
import com.example.driver.DTO.Request.Seller.SellerRequest;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.DTO.Response.Seller.SellerOperationResponse;
import com.example.driver.DTO.Response.Seller.SellerResponse;
import com.example.driver.Entity.Seller;
import com.example.driver.Exception.SellerException;
import com.example.driver.Helper.SellerHelper;
import com.example.driver.Repository.SellerRepository;
import com.example.driver.Service.Interface.SellerService;
import com.example.driver.Tranformer.SellerTransformer;
import com.example.driver.Validations.SellerValidation;
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
        // Won't this step mislead us for Null arguments???
        SellerValidation.sellerEmailValidation(seller); //SellerValidation of Seller if exists throws an Exception
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
        SellerValidation.noSellerEmailValidation(seller,sellerEmail);
        SellerResponse response; //Declaring a SellerResponse
        response=SellerTransformer.sellerResponseFromSeller(seller); //Preparing a Response from Seller Object
        return response; //Returning the Response
    }

    // 3.Get Seller by SellerId
    @Override
    public SellerResponse getSellerBySellerId(Integer sellerId) throws SellerException {
        //Check if there exists any Seller with the given EmailID
        Seller seller=sellerRepository.findById(sellerId).get();
        SellerValidation.noSellerIdValidation(seller,sellerId);
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
        SellerValidation.noSellerEmailValidation(seller,sellerEmail); //Validates the Seller if it doesn't exist throws an Exception
        SellerOperationResponse response=new SellerOperationResponse(); //Initializing an Operation Response
        response.setChanges(SellerHelper.changesMessage(seller,sellerRequest)); // Setting changes message for Response

        Seller updatedSeller=SellerHelper.updateSeller(seller,sellerRequest); //Update the Seller data using Helper
        sellerRepository.save(updatedSeller); //Save the updated Seller
        response.setMessage("Your Seller profile data has been Updated."); //Setting the operation message
        return response; //Returning the Response
    }

    // 6.Delete Seller by Email
    @Override
    public SellerOperationResponse deleteSellerByEmail(String sellerEmail) throws SellerException {
        //Retrieve Seller by Email Address
        Seller seller=sellerRepository.findByEmailId(sellerEmail);
        SellerValidation.noSellerEmailValidation(seller,sellerEmail);
        sellerRepository.delete(seller); //Delete seller from the Database
        SellerOperationResponse response=new SellerOperationResponse(); //Initialising a new Seller Operation Response
        response.setMessage("Seller with email Id "+sellerEmail+" has been deleted."); //Setting the Operation message
        return response; //Returning the Response
    }

    // 7.Delete Seller by Id
    @Override
    public SellerOperationResponse deleteSellerById(Integer sellerId) throws SellerException {
        //Retrieve the Seller using SellerId
        Seller seller=sellerRepository.findById(sellerId).get();
        SellerValidation.noSellerIdValidation(seller,sellerId);
        sellerRepository.delete(seller); //Delete the Seller from the Database
        SellerOperationResponse response=new SellerOperationResponse(); //Initialising a new Operation Response
        response.setMessage("Seller with Id "+"'"+sellerId+"'"+" has been deleted."); //Setting Response Message
        return response; //Returning the Response
    }

    // 8.Get all Sellers by Age
    @Override
    public List<SellerResponse> getAllSellersByAge(Integer age) {
        //Retrieve all the Sellers of a given age from the Database
        List<Seller> sellerList=sellerRepository.findByAge(age);
        //Initialize a new ArrayList for SellerResponse
        List<SellerResponse> responseList=new ArrayList<>();

        for(Seller seller:sellerList){
            //Preparing a Seller Response for each Seller from the retrieved Seller List
            SellerResponse response=SellerTransformer.sellerResponseFromSeller(seller);
            //Adding the prepared response to the ResponseList
            responseList.add(response);
        }
        return responseList; //Returning the Response List
    }

    // 9.Get all Sellers by age less than the given age
    @Override
    public List<SellerResponse> getAllSellersOfAgeLessThan(Integer age) {
        //Retrieve all the Sellers of age less than given age from the Database
        List<Seller> sellerList=sellerRepository.findByAgeYounger(age);
        //Initialize a new ArrayList for SellerResponse
        List<SellerResponse> responseList=new ArrayList<>();

        for(Seller seller:sellerList){
            //Preparing a Seller Response for each Seller from the retrieved Seller List
            SellerResponse response=SellerTransformer.sellerResponseFromSeller(seller);
            //Adding the prepared response to the ResponseList
            responseList.add(response);
        }
        return responseList; //Returning the Response List
    }
}
