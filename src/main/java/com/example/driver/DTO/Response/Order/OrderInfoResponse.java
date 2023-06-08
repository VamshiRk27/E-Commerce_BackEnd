package com.example.driver.DTO.Response.Order;

import com.example.driver.DTO.Response.Item.ItemResponse;
import com.example.driver.Enum.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class OrderInfoResponse {
    String OrderNo;
    Date orderedDate;
    OrderStatus orderStatus;
    ItemResponse item;
}
