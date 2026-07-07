package com.example.accountservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private Long accountId;
    private String tipTransakcije;
    private BigDecimal iznos;
    private LocalDateTime vreme;
    private String statusTransakcije;
}
