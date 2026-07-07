package com.example.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private Long accountId;
    private String poruka;
    private LocalDateTime vreme;
}
