package com.example.cardservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "cards")
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private String brojKartice;

    @Enumerated(EnumType.STRING)
    private TipKartice tipKartice;

    @Enumerated(EnumType.STRING)
    private StatusKartice statusKartice;

    @Column(name = "limit_iznos")
    private BigDecimal limit;
}
