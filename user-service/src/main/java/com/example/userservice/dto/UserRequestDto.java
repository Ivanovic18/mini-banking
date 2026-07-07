package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Ime ne moze biti prazno")
    private String ime;

    @NotBlank(message = "Prezime ne moze biti prazno")
    private String prezime;

    @NotBlank(message = "Email ne moze biti prazan")
    @Email(message = "Email nije u ispravnom formatu")
    private String email;

    @NotBlank(message = "Telefon ne moze biti prazan")
    private String telefon;

    @NotBlank(message = "Korisnicko ime ne moze biti prazno")
    private String username;

    @NotBlank(message = "Lozinka ne moze biti prazna")
    private String password;
}
