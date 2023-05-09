package com.example.driver.DTO.Response.Order;

import com.example.driver.DTO.Response.Item.ItemResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class OrderResponse {
    String customerName;
    String OrderNo;
    ItemResponse item;
    Date orderedDate;
    String cardUsed;
}
