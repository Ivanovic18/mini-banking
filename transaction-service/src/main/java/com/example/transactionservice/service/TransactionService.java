package com.example.transactionservice.service;

import com.example.transactionservice.client.AccountClient;
import com.example.transactionservice.config.RabbitMQConfig;
import com.example.transactionservice.dto.*;
import com.example.transactionservice.entity.StatusTransakcije;
import com.example.transactionservice.entity.Transaction;
import com.example.transactionservice.exception.AccountNotFoundException;
import com.example.transactionservice.exception.LimitExceededException;
import com.example.transactionservice.exception.TransactionNotFoundException;
import com.example.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClientService accountClientService;
    private final AccountClient accountClient;
    private final RabbitTemplate rabbitTemplate;

    @Value("${transaction.max-iznos:10000}")
    private BigDecimal maxIznos;

    public List<TransactionResponseDto> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public TransactionResponseDto getById(Long id) {
        return toResponseDto(findEntity(id));
    }

    public TransactionDetailsDto getDetails(Long id) {
        Transaction transaction = findEntity(id);
        AccountDto account = accountClient.getAccountById(transaction.getAccountId());
        return new TransactionDetailsDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getTipTransakcije(),
                transaction.getIznos(),
                transaction.getVreme(),
                transaction.getStatusTransakcije(),
                account
        );
    }

    public List<TransactionResponseDto> getByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public TransactionResponseDto create(TransactionRequestDto dto) {
        if (dto.getIznos().compareTo(maxIznos) > 0) {
            throw new LimitExceededException("Iznos " + dto.getIznos() + " prelazi maksimalni dozvoljeni iznos transakcije " + maxIznos);
        }

        IshodPromene ishod = accountClientService.promeniStanje(dto.getAccountId(), dto.getTipTransakcije(), dto.getIznos());

        if (ishod == IshodPromene.RACUN_NE_POSTOJI) {
            throw new AccountNotFoundException("Racun ne postoji, id: " + dto.getAccountId());
        }

        StatusTransakcije status = (ishod == IshodPromene.USPESNO) ? StatusTransakcije.USPESNA : StatusTransakcije.NEUSPESNA;

        Transaction transaction = new Transaction();
        transaction.setAccountId(dto.getAccountId());
        transaction.setTipTransakcije(dto.getTipTransakcije());
        transaction.setIznos(dto.getIznos());
        transaction.setVreme(LocalDateTime.now());
        transaction.setStatusTransakcije(status);
        Transaction saved = transactionRepository.save(transaction);

        if (saved.getStatusTransakcije() == StatusTransakcije.USPESNA) {
            TransactionEventDto event = new TransactionEventDto(
                    UUID.randomUUID().toString(),
                    saved.getAccountId(),
                    saved.getTipTransakcije().name(),
                    saved.getIznos()
            );
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
        }

        return toResponseDto(saved);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    private Transaction findEntity(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transakcija nije pronadjena, id: " + id));
    }

    private TransactionResponseDto toResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getTipTransakcije(),
                transaction.getIznos(),
                transaction.getVreme(),
                transaction.getStatusTransakcije()
        );
    }
}
