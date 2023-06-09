package com.example.driver.Repository;

import com.example.driver.Entity.Customer;
import com.example.driver.Enum.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmailId(String emailId);

    @Query(value="select * from customer c where c.age>:age",nativeQuery=true)
    List<Customer> customersGreaterThanAge(Integer age);

    @Query(value="select * from customer c where c.age=:age",nativeQuery=true)
    List<Customer> customersOfGivenAge(Integer age);

    @Query(value="select * from customer c where c.age<:age",nativeQuery=true)
    List<Customer> customersLessThanAge(Integer age);

    @Query(value="select distinct * from customer c where c.card_type=:cardType",nativeQuery=true)
    List<Customer> customersByCardType(CardType cardType);

    @Query(value="select * from customer c where c.address=:%address%",nativeQuery=true)
    List<Customer> customersFromLocation(String address);
}
