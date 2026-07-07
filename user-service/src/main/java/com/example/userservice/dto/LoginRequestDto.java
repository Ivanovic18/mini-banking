package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Korisnicko ime je obavezno")
    private String username;

    @NotBlank(message = "Lozinka je obavezno")
    private String password;
}
