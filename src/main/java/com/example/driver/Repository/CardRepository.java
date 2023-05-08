package com.example.driver.Repository;

import com.example.driver.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    Card findByCardNumber(String cardNumber);
}
