package com.example.driver.Repository;

import com.example.driver.Entity.Card;
import com.example.driver.Enum.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    Card findByCardNumber(String cardNumber);

    @Query(value=" select * from cards c where c.card_type=:cardType",nativeQuery=true)
    List<Card> cardsByCardType(CardType cardType);
}
