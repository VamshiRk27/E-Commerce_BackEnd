package com.example.driver.Repository;

import com.example.driver.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Seller findByEmailId(String emailId);
    List<Seller> findByAge(Integer age);

    @Query(value="select * from seller s where s.age<:age",nativeQuery=true)
    List<Seller> findByAgeYounger(int age);
}
