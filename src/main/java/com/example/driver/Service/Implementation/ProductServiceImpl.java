package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.DTO.Response.Product.ProductResponse;
import com.example.driver.DTO.Response.Seller.AddSellerResponse;
import com.example.driver.Entity.Product;
import com.example.driver.Entity.Seller;
import com.example.driver.Exception.SellerException.SellerException;
import com.example.driver.Repository.ProductRepository;
import com.example.driver.Repository.SellerRepository;
import com.example.driver.Service.Interface.ProductService;
import com.example.driver.Tranformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;

    // 1.Add Product
    @Override
    public AddProductResponse addProduct(AddProductRequest addProductRequest) throws SellerException {
        //Retrieve the Seller using unique identifier seller EmailId
        Seller seller=sellerRepository.findByEmailId(addProductRequest.getSellerEmail());
        if(seller==null){
            //If the seller doesn't exist throw an exception
            throw new SellerException("Seller with email "+"'"+addProductRequest.getSellerEmail()+"'"+" doesn't exist");
        }

        Product product=ProductTransformer.addProductRequestToProduct(addProductRequest); //Prepare the product from Request DTO
        product.setSeller(seller); //Set the Seller for the product
        List<Product> sellerProducts=seller.getProducts(); //Get the list of products sold by Seller
        sellerProducts.add(product); //Add the product in the Seller Products list
        sellerRepository.save(seller); //Save the seller in the Database after modifications
        //Prepare a Product Response from the Transformer function
        AddProductResponse response=ProductTransformer.addProductResponseFromProduct(seller.getName(),product);
        return response; //Return the Response
    }

    // 2.Get all Products of a Category
    @Override
    public List<ProductResponse> getAllProductsByCategory(String category) {
        //Retrieve all the Products of a Category from the Database
        List<Product> productsListOfCategory=productRepository.findAllProductsByCategory(category);
        List<ProductResponse> responseList=new ArrayList<>(); //Initialising a new Response List for Product Response

        //For each Product from the obtained Product List prepare a Product Response
        for(Product product:productsListOfCategory){
            //Preparing Product Response from the Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(product);
            responseList.add(response); //Adding the Product response to the Response List
        }
        return responseList; //Return the ResponseList
    }

    // 3.Get all Products by a Seller
    @Override
    public List<ProductResponse> getProductsBySeller(String sellerEmail) throws SellerException {
        Seller seller=sellerRepository.findByEmailId(sellerEmail); //Search for the seller using emailId as reference
        if(seller==null){
            // If the Seller with given email doesn't exist then throw an Exception
            throw new SellerException("Seller with given Email doesn't exist in the Database");
        }

        List<Product> sellerProductsList=seller.getProducts(); //Get all products from the Seller
        List<ProductResponse> responseList=new ArrayList<>(); //Initialising a new Response List

        //Preparing a response for each Product of Seller from Products List
        for(Product product:sellerProductsList){
            //Preparing product response for each Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(product);
            responseList.add(response); //Add response to Response List
        }
        return responseList; //returning the Response List
    }
}
