package com.example.transactionservice.dto;

import com.example.transactionservice.entity.TipTransakcije;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {

    @NotNull(message = "accountId je obavezan")
    private Long accountId;

    @NotNull(message = "Tip transakcije je obavezan")
    private TipTransakcije tipTransakcije;

    @NotNull(message = "Iznos je obavezan")
    @Positive(message = "Iznos mora biti veci od nule")
    private BigDecimal iznos;
}
