package com.example.driver.Entity;

import com.example.driver.Enum.ProductCategory;
import com.example.driver.Enum.ProductStatus;
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
@Table(name="product") //Used to define the Custom Name for Database Table

public class Product {

    @Id //Defines the attribute as Primary Key of the Product Object
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Provides specification for generation strategy of the Primary Key value
    Integer id; //An integer to store Product ID

    String name; //A String to store Product Name
    Integer price; // An Integer to store Product Price
    Integer quantity; //An Integer to store Product Quantity

    @Enumerated(EnumType.STRING) //Converts the Enum type to String for Database
    ProductCategory productCategory; //An Enum to store Product Category

    @Enumerated(EnumType.STRING) //Converts the Enum type to String for Database
    ProductStatus productStatus; //An Enum to store Product Availability Status

    @ManyToOne //Used to define Child to Parent relationship
    @JoinColumn //Joins the Primary key of the Product with Seller
    Seller seller; //A Seller Object to map the Product with Seller

    @OneToOne(mappedBy="product",cascade=CascadeType.ALL) //Used to define Parent to Child Relationship
    Item item; //An Item Object to map the Product as Individual Item
}
