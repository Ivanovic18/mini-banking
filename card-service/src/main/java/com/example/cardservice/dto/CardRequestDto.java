package com.example.cardservice.dto;

import com.example.cardservice.entity.TipKartice;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardRequestDto {

    @NotNull(message = "accountId je obavezan")
    private Long accountId;

    @NotNull(message = "Tip kartice je obavezan")
    private TipKartice tipKartice;

    @NotNull(message = "Limit je obavezan")
    private BigDecimal limit;
}
