package com.example.driver.Service.Implementation;

import com.example.driver.DTO.Request.Order.OrderRequest;
import com.example.driver.Entity.*;
import com.example.driver.Exception.ProductException;
import com.example.driver.Helper.OrderHelper;
import com.example.driver.Helper.ProductHelper;
import com.example.driver.Repository.OrdersRepository;
import com.example.driver.Service.Interface.OrdersService;
import com.example.driver.Tranformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Override
    public Orders placeOrderFromCart(Customer customer,Card card,Item item) throws ProductException {
        //Preparing a new OrderRequest using customerId,cardNumber & itemId
        OrderRequest orderRequest=new OrderRequest(customer.getCustomerId(), card.getCardNumber(), item.getId());
        Orders order= OrderTransformer.placeOrderFromOrder(orderRequest); //Prepare Order from OrderRequest
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
}
