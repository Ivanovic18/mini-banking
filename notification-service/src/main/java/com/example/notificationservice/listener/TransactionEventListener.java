package com.example.notificationservice.listener;

import com.example.notificationservice.config.RabbitMQConfig;
import com.example.notificationservice.dto.TransactionEventDto;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransactionEventListener {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleTransactionEvent(TransactionEventDto event) {
        if (notificationRepository.existsByEventId(event.getEventId())) {
            return;
        }

        Notification notification = new Notification();
        notification.setEventId(event.getEventId());
        notification.setAccountId(event.getAccountId());
        notification.setPoruka("Transakcija " + event.getTipTransakcije() + ", iznos " + event.getIznos() + ", racun " + event.getAccountId());
        notification.setVreme(LocalDateTime.now());
        notificationRepository.save(notification);
    }

}
