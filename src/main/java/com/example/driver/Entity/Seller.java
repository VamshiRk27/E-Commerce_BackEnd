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
@Table(name="seller") //Used to define the Custom Name for Database Table

public class Seller {

    @Id //Defines the attribute as Primary Key of the Seller Object
    @GeneratedValue(strategy=GenerationType.IDENTITY) //Provides specification for generation strategy of the Primary Key value
    int id; //An Integer to store Seller ID

    String name; //A String to store the Seller Name

    @Column(unique=true,nullable=false) //Enables to customize the Column
    String emailId; //A String to store the Seller Email

    Integer age; //An Integer to store the Seller Age
    String mobileNumber; //A String to store the Seller Mobile Number

    @OneToMany(mappedBy="seller",cascade=CascadeType.ALL) //Used to define Parent to Child Relationship
    List<Product> products=new ArrayList<>(); //A List to store the Products sold by the Seller
}
