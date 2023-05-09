package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Cart.CheckOutCartRequest;
import com.example.driver.DTO.Response.Cart.CartResponse;
import com.example.driver.DTO.Response.Item.ItemResponse;
import com.example.driver.DTO.Response.Order.OrderResponse;
import com.example.driver.Entity.*;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CartException;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Exception.ProductException;
import com.example.driver.Helper.CardHelper;
import com.example.driver.Helper.CartHelper;
import com.example.driver.Helper.ProductHelper;
import com.example.driver.Repository.CardRepository;
import com.example.driver.Repository.CartRepository;
import com.example.driver.Repository.CustomerRepository;
import com.example.driver.Repository.OrdersRepository;
import com.example.driver.Service.Interface.CartService;
import com.example.driver.Service.Interface.OrdersService;
import com.example.driver.Tranformer.CartTransformer;
import com.example.driver.Tranformer.ItemTransformer;
import com.example.driver.Tranformer.OrderTransformer;
import org.hibernate.id.uuid.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    OrdersService ordersService;

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

    // 2.CheckOut Cart
    @Override
    public List<OrderResponse> checkOutCart(CheckOutCartRequest checkOutCartRequest) throws CustomerException, CartException, CardException, ProductException {
        //Get the customer from the Database using unique identifier Key 'Customer EmailId'
        Customer customer=customerRepository.findByEmailId(checkOutCartRequest.getCustomerEmail());
        if(customer==null){
            //If the customer doesn't exist then throw an Exception
            throw new CustomerException("Customer not found");
        }

        Cart cart=customer.getCart(); //Get Cart of the Customer
        if(cart.getItems().size()==0){
            //If the Cart is empty then throw an Exception
            throw new CartException("Cart is Empty");
        }
        //Get the Card from Database using unique identifier Key 'Card Number'
        Card card=cardRepository.findByCardNumber(checkOutCartRequest.getCardNo());
        if(card==null){
            //If card is null throw an Exception
            throw new CardException("Card not found");
        }
        if(card.getCustomer()!=customer){
            //If the given Customer and customer of card are different then, throw an Exception
            throw new CardException("Card doesn't belong to Customer");
        }
        if(!card.getCvv().equals(checkOutCartRequest.getCvv())){
            //If Card Cvv and provided cvv are different then throw an Exception
            throw new CardException("Credentials mismatch\nCannot authenticate Card");
        }
        if(CardHelper.compareDate(card.getExpiryDate(),Date.valueOf(LocalDate.now()))<0){
            //If Date1 is after Date2 then returns value > 0
            //If Date1 is equal to Date2 then returns value 0
            //If Date1 is before Date2 then returns value < 0
            //If the card is Expired then throw an Exception
            throw new CardException("Card is Expired");
        }
        List<OrderResponse> orderResponseList=new ArrayList<>(); //Initialising a new Order Response List
        //For each item in the cart place an Order
        for(Item item:cart.getItems()){
            Orders order=ordersService.placeOrderFromCart(customer,card,item); //Placing the Order from Cart
            OrderResponse response= OrderTransformer.orderResponseFromOrder(order); //Prepare the OrderResponse for the Order
            orderResponseList.add(response); //Add the OrderResponse to the OrderResponseList
        }

        if(orderResponseList.size()==cart.getItems().size()){
            //If the OrderResponseList size is equal to the Cart size then all items are ordered
            //Therefore reset the Cart
            CartHelper.resetCart(cart);
        }
        return orderResponseList; //Return the orderResponseList
    }
}
