package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.DTO.Request.Order.OrderRequest;
import com.example.driver.DTO.Response.Order.OrderInfoResponse;
import com.example.driver.DTO.Response.Order.OrderResponse;
import com.example.driver.Entity.*;
import com.example.driver.Exception.CardException;
import com.example.driver.Exception.CustomerException;
import com.example.driver.Exception.ProductException;
import com.example.driver.Helper.ItemHelper;
import com.example.driver.Helper.OrderHelper;
import com.example.driver.Helper.ProductHelper;
import com.example.driver.Repository.*;
import com.example.driver.Service.Interface.OrdersService;
import com.example.driver.Tranformer.ItemTransformer;
import com.example.driver.Tranformer.OrderTransformer;
import com.example.driver.Validations.CardValidation;
import com.example.driver.Validations.CustomerValidation;
import com.example.driver.Validations.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        OrderHelper.setOrderParameters(order,item,customer);
        ProductHelper.decreaseProductQuantity(order); //Reduce the quantity of the product in the Database once order is ready

        Orders savedOrder=ordersRepository.save(order); //Save the Order in the Database
        return savedOrder; //return the saved Order
    }

    // Place Direct Order from Product
    @Override
    public OrderResponse placeDirectOrder(AddItemRequest addItemRequest,String cardNumber,Integer cvv) throws CustomerException, ProductException, CardException {
        Customer customer=customerRepository.findByEmailId(addItemRequest.getCustomerEmail());
        CustomerValidation.noCustomerEmailValidation(customer,addItemRequest.getCustomerEmail());
        Product product=productRepository.findById(addItemRequest.getProductId()).get();
        ProductValidation.noProductWithIdValidation(product,addItemRequest.getProductId());
        ProductValidation.productRequiredQuantityValidation(product.getQuantity(),addItemRequest.getRequiredQuantity());
        //Get the Card from Database using unique identifier Key 'Card Number'
        Card card=cardRepository.findByCardNumber(cardNumber);
        CardValidation.noCardValidation(card);
        CardValidation.cardOwnerValidation(card,customer);
        CardValidation.cardCVVValidation(card.getCvv(),cvv);

        Item item= ItemTransformer.addItemRequestToItem(addItemRequest); //Preparing Item object using Item Transformer
        ItemHelper.setItemProduct(item,product);
        itemRepository.save(item);
        OrderRequest orderRequest=new OrderRequest(customer.getCustomerId(),cardNumber,item.getItemId());
        Orders order=OrderTransformer.placeDirectOrder(orderRequest);
        OrderHelper.setOrderParameters(order,item,customer);
        ProductHelper.decreaseProductQuantity(order); //Reduce the quantity of the product in the Database once order is ready
        Orders savedOrder=ordersRepository.save(order); //Save the Order in the Database
        OrderResponse response= OrderTransformer.orderResponseFromOrder(savedOrder);
        return response;
    }

    // 2.Get all the orders for a customer
    @Override
    public List<OrderInfoResponse> getAllOrdersForCustomer(Integer customerId) throws CustomerException {
        Customer customer=customerRepository.findById(customerId).get();
        CustomerValidation.noCustomerIDValidation(customer,customerId);
        List<Orders> customerOrders=customer.getOrderList();
        List<OrderInfoResponse> responseList=new ArrayList<>();
        for(Orders order : customerOrders){
            OrderInfoResponse response=OrderTransformer.orderInfoResponseFromOrder(order);
            responseList.add(response);
        }
        return responseList;
    }
}
