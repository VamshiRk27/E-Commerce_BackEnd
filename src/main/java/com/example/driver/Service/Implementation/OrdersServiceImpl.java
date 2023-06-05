package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.DTO.Request.Order.OrderRequest;
import com.example.driver.DTO.Response.Order.OrderResponse;
import com.example.driver.Entity.*;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Exception.ProductException;
import com.example.driver.Helper.ProductHelper;
import com.example.driver.Repository.*;
import com.example.driver.Service.Interface.OrdersService;
import com.example.driver.Tranformer.ItemTransformer;
import com.example.driver.Tranformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CardRepository cardRepository;

    // Place Order From Cart
    @Override
    public Orders placeOrderFromCart(Customer customer,Card card,Item item) throws ProductException {
        //Preparing a new OrderRequest using customerId,cardNumber & itemId
        OrderRequest orderRequest=new OrderRequest(customer.getCustomerId(), card.getCardNumber(), item.getItemId());
        Orders order= OrderTransformer.placeOrderFromCart(orderRequest); //Prepare Order from OrderRequest
        //Set the remaining parameters for the Order placed
        order.setTotalOrderValue(item.getProduct().getPrice()*item.getRequiredQuantity()); //Setting the Total Order Value
        order.setCustomer(customer); //Set the customer for the Order
        order.setItem(item); //Set the item for the Order
        customer.getOrderList().add(order); //Add the Order to the customer Order List
        item.setOrders(order); //Set the order for the item object in the Cart
        ProductHelper.decreaseProductQuantity(order); //Reduce the quantity of the product in the Database once order is ready

        Orders savedOrder=ordersRepository.save(order); //Save the Order in the Database
        return savedOrder; //return the saved Order
    }

    // Place Direct Order from Product
    @Override
    public OrderResponse placeDirectOrder(AddItemRequest addItemRequest,String cardNumber,Integer cvv) throws CustomerException, ProductException, CardException {
        Customer customer=customerRepository.findByEmailId(addItemRequest.getCustomerEmail());
        if(customer==null){
            throw new CustomerException("Customer not found");
        }
        Product product=productRepository.findById(addItemRequest.getProductId()).get();
        if(product==null){
            throw new ProductException("Product not found");
        }
        if(product.getQuantity()<addItemRequest.getRequiredQuantity()){
            throw new ProductException("Required quantity not available");
        }
        //Get the Card from Database using unique identifier Key 'Card Number'
        Card card=cardRepository.findByCardNumber(cardNumber);
        if(card==null){
            //If card is null throw an Exception
            throw new CardException("Card not found");
        }
        if(card.getCustomer()!=customer){
            //If the given Customer and customer of card are different then, throw an Exception
            throw new CardException("Card doesn't belong to Customer");
        }
        if(!card.getCvv().equals(cvv)){
            //If Card Cvv and provided cvv are different then throw an Exception
            throw new CardException("Credentials mismatch\nCannot authenticate Card");
        }
        Item item= ItemTransformer.addItemRequestToItem(addItemRequest); //Preparing Item object using Item Transformer
        item.setName(product.getName()); //Setting the Item name using Product name
        item.setProduct(product); //Mapping the Product for Item object
        product.getItemList().add(item);
        itemRepository.save(item);
        OrderRequest orderRequest=new OrderRequest(customer.getCustomerId(),cardNumber,item.getItemId());
        Orders order=OrderTransformer.placeDirectOrder(orderRequest);
        order.setTotalOrderValue(item.getProduct().getPrice()*item.getRequiredQuantity()); //Setting the Total Order Value
        order.setCustomer(customer); //Set the customer for the Order
        order.setItem(item); //Set the item for the Order
        customer.getOrderList().add(order); //Add the Order to the customer Order List
        item.setOrders(order); //Set the order for the item object in the Cart
        ProductHelper.decreaseProductQuantity(order); //Reduce the quantity of the product in the Database once order is ready
        Orders savedOrder=ordersRepository.save(order); //Save the Order in the Database
        OrderResponse response= OrderTransformer.orderResponseFromOrder(savedOrder);
        return response;
    }
}
