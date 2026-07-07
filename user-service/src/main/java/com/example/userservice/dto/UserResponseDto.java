package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String username;
}
