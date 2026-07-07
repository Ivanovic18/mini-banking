package com.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEventDto {
    private String eventId;
    private Long accountId;
    private String tipTransakcije;
    private BigDecimal iznos;
}
