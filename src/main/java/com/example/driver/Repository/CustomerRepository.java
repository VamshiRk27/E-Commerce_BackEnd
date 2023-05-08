package com.example.driver.Repository;

import com.example.driver.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmailId(String emailId);

    @Query(value="select * from customer c where c.age>:age",nativeQuery=true)
    List<Customer> customersGreaterThanAge(Integer age);
}
