package com.example.driver.Tranformer;

import com.example.driver.DTO.Request.Cart.CheckOutCartRequest;
import com.example.driver.DTO.Request.Order.OrderRequest;
import com.example.driver.DTO.Response.Order.OrderResponse;
import com.example.driver.Entity.Orders;
import com.example.driver.Helper.OrderHelper;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class OrderTransformer{
    public static Orders placeOrderFromOrder(OrderRequest orderRequest){
        String maskedCardNumber=OrderHelper.generateMaskedCard(orderRequest.getCardNumber());
        return Orders.builder()
                .orderNo(String.valueOf(UUID.randomUUID()))
                .cardUsed(maskedCardNumber)
                .build();
    }
    public static OrderResponse orderResponseFromOrder(Orders order){
        return OrderResponse.builder()
                .customerName(order.getCustomer().getName())
                .OrderNo(order.getOrderNo())
                .orderedDate(order.getOrderedDate())
                .cardUsed(order.getCardUsed())
                .item(ItemTransformer.itemResponseFromItem(order.getItem()))
                .build();
    }
}
