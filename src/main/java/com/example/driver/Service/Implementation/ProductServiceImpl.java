package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Product.AddProductRequest;
import com.example.driver.DTO.Response.Product.AddProductResponse;
import com.example.driver.DTO.Response.Product.ProductOperationResponse;
import com.example.driver.DTO.Response.Product.ProductResponse;
import com.example.driver.Entity.Product;
import com.example.driver.Entity.Seller;
import com.example.driver.Enum.ProductCategory;
import com.example.driver.Enum.ProductStatus;
import com.example.driver.Exception.ProductException;
import com.example.driver.Exception.SellerException;
import com.example.driver.Repository.ProductRepository;
import com.example.driver.Repository.SellerRepository;
import com.example.driver.Service.Interface.ProductService;
import com.example.driver.Tranformer.ProductTransformer;
import com.example.driver.Validations.ProductValidation;
import com.example.driver.Validations.SellerValidation;
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
        //If Seller doesn't exist will throw an Exception
        SellerValidation.noSellerEmailValidation(seller,addProductRequest.getSellerEmail());
        Product product=ProductTransformer.addProductRequestToProduct(addProductRequest); //Prepare the product from Request DTO

        // 2 way mapping for Product <---> Seller
        product.setSeller(seller); //Set the Seller for the product
        List<Product> sellerProducts=seller.getProducts(); //Get the list of products sold by Seller
        sellerProducts.add(product); //Add the product in the Seller Products list

        sellerRepository.save(seller); //Save the parent model Seller in the Database after modifications
        //Prepare a Product Response from the Transformer function
        AddProductResponse response=ProductTransformer.addProductResponseFromProduct(seller.getName(),product);
        return response; //Return the Response
    }

    // 2.Get all Products of a Category
    @Override
    public List<ProductResponse> getAllProductsByCategory(ProductCategory category) {
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
        // If Seller doesn't exist throws an Exception
        SellerValidation.noSellerEmailValidation(seller,sellerEmail);

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
        // If the Seller with given email doesn't exist then throw an Exception
        SellerValidation.noSellerEmailValidation(seller,sellerEmail);
        //Search for the product using the given ProductId from the Database
        Product product=productRepository.findById(productId).get();
        //If the Product doesn't exist then throw an Exception
        ProductValidation.noProductWithIdValidation(product,productId);
        ProductOperationResponse response = new ProductOperationResponse(); //Initialising an Operation Response

        //If the seller retrieved and product seller are same
        if(product.getSeller()==seller){
            List<Product> sellerProductsList=seller.getProducts(); //Get the products sold by Seller
            sellerProductsList.remove(product); //Remove the product from the list
            productRepository.delete(product); //Delete the product from Database
            sellerRepository.save(seller); //Update the seller by saving in the Database
            response.setMessage("The Product has been removed Successfully"); //Set the Response Message
        }
        //If the seller retrieved and product seller are different
        else{
            //Set the Response message
            response.setMessage("The Product with id "+"'"+productId+"'"+" is not sold by Seller "+seller.getName());
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

    // 7.Get all out_of_stock products
    @Override
    public List<ProductResponse> getAllOutOfStockProducts() {
        //Get all the Products List which are available in the inventory
        List<Product> outOfStockProducts=productRepository.productsByStatus(ProductStatus.OUT_OF_STOCK);
        List<ProductResponse> responseList=new ArrayList<>(); //Initializing a new ArrayList
        //Prepare Product Response for all the available products
        for(Product product:outOfStockProducts){
            //Preparing response for each Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(product);
            responseList.add(response); //Add response to the Response list
        }
        return responseList; //Return the Response list
    }

    // 8.Get all Available Products
    @Override
    public List<ProductResponse> getAllAvailableProducts() {
        //Get all the Products List which are available in the inventory
        List<Product> availableProducts=productRepository.productsByStatus(ProductStatus.AVAILABLE);
        List<ProductResponse> responseList=new ArrayList<>(); //Initializing a new ArrayList
        //Prepare Product Response for all the available products
        for(Product product:availableProducts){
            //Preparing response for each Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(product);
            responseList.add(response); //Add response to the Response list
        }
        return responseList; //Return the Response list
    }

    // 9.Get all the products with low inventory
    @Override
    public List<ProductResponse> getAllLowInventoryProducts() {
        //Get all the Products List which are available in the inventory
        List<Product> lowInventoryProducts=productRepository.productsByInventoryStatus(50);
        List<ProductResponse> responseList=new ArrayList<>(); //Initializing a new ArrayList
        //Prepare Product Response for all the available products
        for(Product product:lowInventoryProducts){
            //Preparing response for each Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(product);
            responseList.add(response); //Add response to the Response list
        }
        return responseList; //Return the Response list
    }

    // 10.Get the cheapest product in a particular category
    @Override
    public ProductResponse getCheapestProductInCategory(ProductCategory category) {
        List<Product> products=productRepository.cheapestProductInCategory(category);
        ProductResponse response=ProductTransformer.productResponseFromProduct(products.get(0));
        return response;
    }

    // 11.Return the costliest product in a particular category
    @Override
    public ProductResponse getCostliestProductInCategory(ProductCategory category) {
        List<Product> products=productRepository.costliestProductInCategory(category);
        ProductResponse response=ProductTransformer.productResponseFromProduct(products.get(0));
        return response;
    }

    // 12.Get all low inventory products of a category
    @Override
    public List<ProductResponse> getAllLowInventoryProductsOfCategory(ProductCategory category) {
        //Get all the Products List which are available in the inventory
        List<Product> lowProductsInCategory=productRepository.productsByInventoryStatusAndCategory(50,category);
        List<ProductResponse> responseList=new ArrayList<>(); //Initializing a new ArrayList
        //Prepare Product Response for all the available products
        for(Product product:lowProductsInCategory){
            //Preparing response for each Product
            ProductResponse response=ProductTransformer.productResponseFromProduct(product);
            responseList.add(response); //Add response to the Response list
        }
        return responseList; //Return the Response list
    }


}
