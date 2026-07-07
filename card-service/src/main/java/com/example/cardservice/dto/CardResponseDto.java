package com.example.cardservice.dto;

import com.example.cardservice.entity.StatusKartice;
import com.example.cardservice.entity.TipKartice;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CardResponseDto {
    private Long id;
    private Long accountId;
    private String brojKartice;
    private TipKartice tipKartice;
    private StatusKartice statusKartice;
    private BigDecimal limit;
}
