package com.example.accountservice.service;

import com.example.accountservice.client.CardClient;
import com.example.accountservice.client.TransactionClient;
import com.example.accountservice.client.UserClient;
import com.example.accountservice.dto.*;
import com.example.accountservice.entity.Account;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.InsufficientFundsException;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserClient userClient;
    private final CardClient cardClient;
    private final TransactionClient transactionClient;

    public List<AccountResponseDto> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public AccountResponseDto getById(Long id) {
        return toResponseDto(findEntity(id));
    }

    public AccountOverviewDto getOverview(Long id) {
        Account account = findEntity(id);
        UserDto user = userClient.getUserById(account.getUserId());
        List<CardDto> cards = cardClient.getCardsByAccountId(id);
        List<TransactionDto> transactions = transactionClient.getTransactionsByAccountId(id);

        return new AccountOverviewDto(
                account.getId(),
                account.getUserId(),
                account.getBrojRacuna(),
                account.getStanje(),
                account.getVrstaRacuna(),
                user,
                cards,
                transactions
        );
    }

    public AccountResponseDto create(AccountRequestDto dto) {
        proveriKorisnika(dto.getUserId());
        Account account = new Account();
        account.setUserId(dto.getUserId());
        account.setVrstaRacuna(dto.getVrstaRacuna());
        account.setBrojRacuna(generisiBrojRacuna());
        account.setStanje(BigDecimal.ZERO);
        Account saved = accountRepository.save(account);
        return toResponseDto(saved);
    }

    public AccountResponseDto update(Long id, AccountRequestDto dto) {
        Account account = findEntity(id);
        proveriKorisnika(dto.getUserId());
        account.setUserId(dto.getUserId());
        account.setVrstaRacuna(dto.getVrstaRacuna());
        Account saved = accountRepository.save(account);
        return toResponseDto(saved);
    }

    public AccountResponseDto deposit(Long id, BigDecimal iznos) {
        Account account = findEntity(id);
        account.setStanje(account.getStanje().add(iznos));
        Account saved = accountRepository.save(account);
        return toResponseDto(saved);
    }

    public AccountResponseDto withdraw(Long id, BigDecimal iznos) {
        Account account = findEntity(id);
        if (account.getStanje().compareTo(iznos) < 0) {
            throw new InsufficientFundsException("Nedovoljno sredstava na racunu, id: " + id);
        }
        account.setStanje(account.getStanje().subtract(iznos));
        Account saved = accountRepository.save(account);
        return toResponseDto(saved);
    }

    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    // Feign
    private void proveriKorisnika(Long userId) {
        try {
            userClient.getUserById(userId);
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException("Korisnik ne postoji: " + userId);
        }
    }

    private String generisiBrojRacuna() {
        String broj;
        do {
            long random = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
            broj = "265-" + random;
        } while (accountRepository.existsByBrojRacuna(broj));
        return broj;
    }

    private Account findEntity(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Racun nije pronadjen, id: " + id));
    }

    private AccountResponseDto toResponseDto(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getUserId(),
                account.getBrojRacuna(),
                account.getStanje(),
                account.getVrstaRacuna()
        );
    }
}
