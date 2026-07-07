package com.example.cardservice.repository;

import com.example.cardservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByAccountId(Long accountId);

    boolean existsByBrojKartice(String brojKartice);
}
