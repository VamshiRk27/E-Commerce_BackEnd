package com.example.driver.Entity;

import com.example.driver.Enum.CardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity //Specifies this class is an Entity & is mapped to a Database Table
@AllArgsConstructor //Generates an All Arguments Constructor
@NoArgsConstructor //Generates a No Arguments Constructor
@Builder //Used to build the Object
@Data //Contains/Generates all the Getters & Setters
// @Data contains @ToString , @ReqArgsConstructor , @Getter , @Setter
@FieldDefaults(level= AccessLevel.PRIVATE) //Defines the scope of Attributes of the Class/Entity
@Table(name="card") //Used to define the Custom Name for Database Table

public class Card {

    @Id //Defines the attribute as Primary Key of the Product Object
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Provides specification for generation strategy of the Primary Key value
    Integer id; //An integer to store Card ID

    @Column(unique=true,nullable=false) //Enables to customize the Column
    String cardNumber; //A String to store Card Number
    Integer cvv; //An Integer to store Card CVV
    Date expiryDate; //A Date to store Card Expiry

    @Enumerated(EnumType.STRING) //Converts the Enum type to String for Database
    CardType cardType; //An Enum to store Card Type

    @ManyToOne //Used to define Child to Parent relationship
    @JoinColumn //Joins the Primary key of the Card with Customer as Foreign Key
    Customer customer; //A Customer Object to map the Card with Customer
}
