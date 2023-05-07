package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.DTO.Response.Product.ProductOperationResponse;
import com.example.driver.DTO.Response.Product.ProductResponse;
import com.example.driver.Entity.Product;
import com.example.driver.Entity.Seller;
import com.example.driver.Exception.ProductException;
import com.example.driver.Exception.SellerException;
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

    // 4.Delete a Product by sellerEmail and ProductId
    @Override
    public ProductOperationResponse deleteASellerProduct(String sellerEmail, Integer productId) throws SellerException, ProductException {
        //Search for the seller using emailId as reference
        Seller seller=sellerRepository.findByEmailId(sellerEmail);
        if(seller==null){
            // If the Seller with given email doesn't exist then throw an Exception
            throw new SellerException("Seller with given Email doesn't exist in the Database");
        }
        //Search for the product using the given ProductId from the Database
        Product product=productRepository.findById(productId).get();
        if(product==null){
            //If the Product is null then throw an Exception
            throw new ProductException("Product with given id "+productId+" doesn't exist");
        }
        ProductOperationResponse response = new ProductOperationResponse(); //Initialising an Operation Response

        if(product.getSeller()==seller){ //If the seller retrieved and product seller are same
            List<Product> sellerProductsList=seller.getProducts(); //Get the products sold by Seller
            sellerProductsList.remove(product); //Remove the product from the list
            productRepository.delete(product); //Delete the product from Database
            sellerRepository.save(seller); //Update the seller by saving in the Database
            response.setMessage("The Product has been removed Successfully"); //Set the Response Message
        }
        else{ //If the seller retrieved and product seller are different
            //Set the Response message
            response.setMessage("The Product with given id is not sold by the given Seller");
        }
        return response; //Return the response
    }

    // 5.Get Top 5 Cheapest products
    @Override
    public List<ProductResponse> getTopFiveCheapestProducts() {
        //Get all the Products List in the order of Low price to High price
        List<Product> CheapProducts=productRepository.CheapestProducts();
        List<ProductResponse> responseList=new ArrayList<>(); //Initialize a new ArrayList
        //Prepare Product Response for top 5 Cheapest Products
        for(int i=0;i<5;i++){
            //Preparing response for each Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(CheapProducts.get(i));
            responseList.add(response); //Add response to the Response list
        }
        return responseList; //Return the Response list
    }

    // 6.Get Top 5 Costliest Products
    @Override
    public List<ProductResponse> getTopFiveCostliestProducts() {
        //Get all the Products List in the order of High price to Low price
        List<Product> CostlyProducts=productRepository.CostliestProducts();
        List<ProductResponse> responseList=new ArrayList<>(); //Initializing a new ArrayList
        //Prepare Product Response for top 5 Costliest Products
        for(int i=0;i<5;i++){
            //Preparing response for each Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(CostlyProducts.get(i));
            responseList.add(response); //Add response to the Response list
        }
        return responseList; //Return the Response list
    }

    // 8.Get all Available Products

}
