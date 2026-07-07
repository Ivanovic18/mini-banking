package com.example.accountservice.dto;

import com.example.accountservice.entity.VrstaRacuna;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountOverviewDto {
    private Long id;
    private Long userId;
    private String brojRacuna;
    private BigDecimal stanje;
    private VrstaRacuna vrstaRacuna;
    private UserDto vlasnik;
    private List<CardDto> kartice;
    private List<TransactionDto> transakcije;
}
