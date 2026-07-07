package com.example.accountservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String username;
}
