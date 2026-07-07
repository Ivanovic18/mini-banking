package com.example.accountservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String brojRacuna;
    private BigDecimal stanje;

    @Enumerated(EnumType.STRING)
    private VrstaRacuna vrstaRacuna;
}
