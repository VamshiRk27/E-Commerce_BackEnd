package com.example.driver.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity //Specifies this class is an Entity & is mapped to a Database Table
@AllArgsConstructor //Generates an All Arguments Constructor
@NoArgsConstructor //Generates a No Arguments Constructor
@Builder //Used to build the Object
@Data //Contains/Generates all the Getters & Setters
// @Data contains @ToString , @ReqArgsConstructor , @Getter , @Setter
@FieldDefaults(level= AccessLevel.PRIVATE) //Defines the scope of Attributes of the Class/Entity
@Table(name="item") //Used to define the Custom Name for Database Table

public class Item {

    @Id //Defines the attribute as Primary Key of the Item Object
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Provides specification for generation strategy of the Primary Key value
    Integer itemId; //An Integer to store the Item ID

    String name; //A String to store the Item Name
    Integer requiredQuantity; //An Integer to store the Required Item Quantity
    //cost/price can be obtained from product class

    @ManyToOne //Used to define Child to Parent relationship
    @JoinColumn //Joins the Primary key of the Item with Cart
    Cart cart; //A Cart object to map the Item with Cart

    @OneToOne //Used to define Child to Parent relationship
    @JoinColumn //Joins the Primary key of the Item with Order
    Orders orders; //An Orders object to map the Item with Order

    @ManyToOne //Used to define Child to Parent relationship
    @JoinColumn //Joins the Primary key of the Item with Product
    Product product; //A Product object to map the Item with Product
}
