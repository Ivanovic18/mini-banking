package com.example.accountservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CardDto {
    private Long id;
    private Long accountId;
    private String brojKartice;
    private String tipKartice;
    private String statusKartice;
    private BigDecimal limit;
}
