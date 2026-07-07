package com.example.transactionservice.dto;

import com.example.transactionservice.entity.StatusTransakcije;
import com.example.transactionservice.entity.TipTransakcije;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionDetailsDto {
    private Long id;
    private Long accountId;
    private TipTransakcije tipTransakcije;
    private BigDecimal iznos;
    private LocalDateTime vreme;
    private StatusTransakcije statusTransakcije;
    private AccountDto racun;
}
