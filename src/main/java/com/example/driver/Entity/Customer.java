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
@Table(name="customer") //Used to define the Custom Name for Database Table

public class Customer {

    @Id //Defines the attribute as Primary Key of the Customer Object
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Provides specification for generation strategy of the Primary Key value
    Integer customerId; //An integer to store Customer ID

    String name; //A String to store Customer name

    @Column(unique=true,nullable=false) //Enables to customize the Column
    String emailId; //A String to store Customer Email-Id

    Integer age; //An Integer to store Customer Age
    String mobNo; //A String to store Customer Mobile Number
    String address; //A String to store Customer Address

    @OneToMany(mappedBy="customer",cascade=CascadeType.ALL) //Used to define Parent to Child Relationship
    List<Card> cards=new ArrayList<>(); //A list to store Payment Cards used by Customer

    @OneToOne(mappedBy="customer",cascade=CascadeType.ALL) //Used to define Parent to Child Relationship
    //A new empty Cart is to be created when a new customer profile is created
    Cart cart; //A shopping cart for Customer

    @OneToMany(mappedBy="customer",cascade=CascadeType.ALL) //Used to define Parent to Child Relationship
    List<Orders> orderList=new ArrayList<>(); //An ordersList to store the ordered Items for a Customer
}
