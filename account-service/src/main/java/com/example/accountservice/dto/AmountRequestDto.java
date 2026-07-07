package com.example.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmountRequestDto {

    @NotNull(message = "Iznos je obavezan")
    @Positive(message = "Iznos mora biti veci od nule")
    private BigDecimal iznos;
}
