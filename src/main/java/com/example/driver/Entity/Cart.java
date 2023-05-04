package com.example.driver.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity //Specifies this class is an Entity & is mapped to a Database Table
@AllArgsConstructor //Generates an All Arguments Constructor
@NoArgsConstructor //Generates a No Arguments Constructor
@Builder //Used to build the Object
@Data //Contains/Generates all the Getters & Setters
// @Data contains @ToString , @ReqArgsConstructor , @Getter , @Setter
@FieldDefaults(level= AccessLevel.PRIVATE) //Defines the scope of Attributes of the Class/Entity
@Table(name="cart") //Used to define the Custom Name for Database Table

public class Cart {

    @Id //Defines the attribute as Primary Key of the Product Object
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Provides specification for generation strategy of the Primary Key value
    Integer id; //An Integer to store the Cart ID

    Integer cartTotal; //An Integer to store the total amount for all Cart items
    Integer numberOfItems; //An Integer to store the number of Cart items

    @OneToOne //Used to define Child to Parent relationship
    @JoinColumn //Joins the Primary key of the Cart with Customer as Foreign Key
    Customer customer; //A Customer Object to map the Cart with Customer

    @OneToMany(mappedBy="cart",cascade=CascadeType.ALL) //Used to define Parent to Child Relationship
    List<Item> items=new ArrayList<>(); //A List of Ordered Items in the Cart
}
