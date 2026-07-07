package com.example.cardservice.service;

import com.example.cardservice.client.AccountClient;
import com.example.cardservice.dto.CardRequestDto;
import com.example.cardservice.dto.CardResponseDto;
import com.example.cardservice.entity.Card;
import com.example.cardservice.entity.StatusKartice;
import com.example.cardservice.exception.AccountNotFoundException;
import com.example.cardservice.exception.CardNotFoundException;
import com.example.cardservice.repository.CardRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final AccountClient accountClient;

    public List<CardResponseDto> getAll() {
        return cardRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public CardResponseDto getById(Long id) {
        return toResponseDto(findEntity(id));
    }

    public List<CardResponseDto> getByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public CardResponseDto create(CardRequestDto dto) {
        proveriRacun(dto.getAccountId());

        Card card = new Card();
        card.setAccountId(dto.getAccountId());
        card.setBrojKartice(generisiBrojKartice());
        card.setTipKartice(dto.getTipKartice());
        card.setStatusKartice(StatusKartice.AKTIVNA);
        card.setLimit(dto.getLimit());
        Card saved = cardRepository.save(card);
        return toResponseDto(saved);
    }

    public CardResponseDto update(Long id, CardRequestDto dto) {
        Card card = findEntity(id);
        proveriRacun(dto.getAccountId());

        card.setAccountId(dto.getAccountId());
        card.setTipKartice(dto.getTipKartice());
        card.setLimit(dto.getLimit());
        Card saved = cardRepository.save(card);
        return toResponseDto(saved);
    }

    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    public CardResponseDto block(Long id) {
        Card card = findEntity(id);
        card.setStatusKartice(StatusKartice.BLOKIRANA);
        Card saved = cardRepository.save(card);
        return toResponseDto(saved);
    }

    public CardResponseDto unblock(Long id) {
        Card card = findEntity(id);
        card.setStatusKartice(StatusKartice.AKTIVNA);
        Card saved = cardRepository.save(card);
        return toResponseDto(saved);
    }

    // Feign provera
    private void proveriRacun(Long accountId) {
        try {
            accountClient.getAccountById(accountId); // Ako vrati racun, znaci da racun postoji
        } catch (FeignException.NotFound e) {
            throw new AccountNotFoundException("Racun ne postoji, id: " + accountId);
        }
    }

    private String generisiBrojKartice() {
        String broj;
        do {
            long random = ThreadLocalRandom.current().nextLong(1_000_000_000_000_000L, 10_000_000_000_000_000L);
            broj = String.valueOf(random);
        } while (cardRepository.existsByBrojKartice(broj));
        return broj;
    }

    private Card findEntity(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Kartica nije pronadjena, id: " + id));
    }

    private CardResponseDto toResponseDto(Card card) {
        return new CardResponseDto(
                card.getId(),
                card.getAccountId(),
                card.getBrojKartice(),
                card.getTipKartice(),
                card.getStatusKartice(),
                card.getLimit()
        );
    }
}
