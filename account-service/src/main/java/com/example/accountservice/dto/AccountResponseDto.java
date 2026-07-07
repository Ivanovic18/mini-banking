package com.example.accountservice.dto;

import com.example.accountservice.entity.VrstaRacuna;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountResponseDto {
    private Long id;
    private Long userId;
    private String brojRacuna;
    private BigDecimal stanje;
    private VrstaRacuna vrstaRacuna;
}
