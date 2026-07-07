package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationResponseDto;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationResponseDto> getAll() {
        return notificationRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    private NotificationResponseDto toResponseDto(Notification notification) {
        return new NotificationResponseDto(
                notification.getId(),
                notification.getAccountId(),
                notification.getPoruka(),
                notification.getVreme()
        );
    }
}
