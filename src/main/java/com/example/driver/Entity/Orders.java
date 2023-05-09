package com.example.driver.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity //Specifies this class is an Entity & is mapped to a Database Table
@AllArgsConstructor //Generates an All Arguments Constructor
@NoArgsConstructor //Generates a No Arguments Constructor
@Builder //Used to build the Object
@Data //Contains/Generates all the Getters & Setters
// @Data contains @ToString , @ReqArgsConstructor , @Getter , @Setter
@FieldDefaults(level= AccessLevel.PRIVATE) //Defines the scope of Attributes of the Class/Entity
@Table(name="orders") //Used to define the Custom Name for Database Table

public class Orders {
    @Id //Defines the attribute as Primary Key of the Product Object
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Provides specification for generation strategy of the Primary Key value
    Integer orderId; //An Integer to store the Order ID


    String orderNo; //A String to store the Order Number
    Integer totalOrderValue; //An Integer to store the Total Order Value
    @CreationTimestamp
    Date orderedDate; //A Date to store the Order Date
    String cardUsed; //A String to store the Card used for Order

    @ManyToOne //Used to define Child to Parent relationship
    @JoinColumn //Joins the Primary key of the Order with Customer
    Customer customer; //A Customer Object to map the Order with Customer

    @OneToOne(mappedBy="orders",cascade=CascadeType.ALL) //Used to define Parent to Child Relationship
    Item item; //A List to store the Order Items
}
