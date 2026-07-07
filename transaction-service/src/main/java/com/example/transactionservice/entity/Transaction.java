package com.example.transactionservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    @Enumerated(EnumType.STRING)
    private TipTransakcije tipTransakcije;

    private BigDecimal iznos;
    private LocalDateTime vreme;

    @Enumerated(EnumType.STRING)
    private StatusTransakcije statusTransakcije;
}
