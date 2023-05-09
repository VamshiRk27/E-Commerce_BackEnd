package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Response.Cart.CartResponse;
import com.example.driver.DTO.Response.Item.ItemResponse;
import com.example.driver.Entity.Cart;
import com.example.driver.Entity.Customer;
import com.example.driver.Entity.Item;
import com.example.driver.Repository.CartRepository;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Service.Interface.CartService;
import com.example.driver.Tranformer.CartTransformer;
import com.example.driver.Tranformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;

    // 1.Add the saved Item to the Customer cart
    @Override
    public CartResponse saveCart(String customerEmail, Item item) {
        //Retrieve the customer from the Database using unique identifier key 'Customer EmailId'
        Customer customer=customerRepository.findByEmailId(customerEmail);
        Cart cart=customer.getCart(); //Getting the cart of Customer

        //Setting the parameters of Cart
        cart.setCartTotal(cart.getCartTotal()+(item.getProduct().getPrice()*item.getRequiredQuantity()));
        cart.getItems(); //Get the Cart Items of Customer
        cart.setNumberOfItems(cart.getItems().size());
        cartRepository.save(cart);



        List<ItemResponse> itemResponseList=new ArrayList<>(); //Initiating a new ArrayList for Item Response
        for(Item itemProfile: cart.getItems()){
            //Preparing an Item Response for each Item in the cart
            ItemResponse itemResponse=ItemTransformer.itemResponseFromItem(itemProfile);
            itemResponseList.add(itemResponse); //Adding the prepared response to final ResponseList
        }
        //Preparing Cart Response for Customer cart
        CartResponse response=CartTransformer.cartResponseFromCart(cart);
        response.setItems(itemResponseList); //Setting ItemResponseList to Cart
        return response; //Returning the Response
    }
}
