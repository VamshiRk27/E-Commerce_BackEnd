package com.example.driver.Helper;

import com.example.driver.Entity.Customer;
import com.example.driver.Entity.Item;
import com.example.driver.Entity.Orders;
import com.example.driver.Enum.OrderStatus;

public class OrderHelper {
    public static String generateMaskedCard(String cardNumber){
        String maskedCardNumber="";
        for(int i=0;i<cardNumber.length()-4;i++){
            maskedCardNumber+="X";
        }
        maskedCardNumber+=cardNumber.substring((cardNumber.length()-4));
        return maskedCardNumber;
    }
    public static void setOrderParameters(Orders order,Item item,Customer customer){
        order.setTotalOrderValue(item.getProduct().getPrice()*item.getRequiredQuantity()); //Setting the Total Order Value
        order.setCustomer(customer); //Set the customer for the Order
        order.setItem(item); //Set the item for the Order
        customer.getOrderList().add(order); //Add the Order to the customer Order List
        item.setOrders(order); //Set the order for the item object in the Cart
        order.setOrderStatus(OrderStatus.CONFIRMED);
    }
}
