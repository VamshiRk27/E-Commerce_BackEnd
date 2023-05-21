package com.example.driver.Controller;

import com.example.driver.DTO.Request.Item.AddItemRequest;
import com.example.driver.DTO.Response.Order.OrderResponse;
import com.example.driver.Service.Interface.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrdersService ordersService;

    // 1.Add Order from Product
    @PostMapping("/place-direct-order")
    public ResponseEntity placeDirectOrder(@RequestBody AddItemRequest addItemRequest,
                                           @RequestParam("cardNumber") String cardNumber,
                                           @RequestParam("cvv") Integer cvv)
    {
        try{
            OrderResponse response=ordersService.placeDirectOrder(addItemRequest,cardNumber,cvv);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
