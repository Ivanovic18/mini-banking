package com.example.transactionservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private Long userId;
    private String brojRacuna;
    private BigDecimal stanje;
}
