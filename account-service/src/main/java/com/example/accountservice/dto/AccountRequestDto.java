package com.example.accountservice.dto;

import com.example.accountservice.entity.VrstaRacuna;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequestDto {

    @NotNull(message = "userId je obavezan")
    private Long userId;

    @NotNull(message = "Vrsta racuna je obavezna")
    private VrstaRacuna vrstaRacuna;
}
