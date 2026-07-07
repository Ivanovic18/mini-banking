package com.example.cardservice.controller;

import com.example.cardservice.dto.CardRequestDto;
import com.example.cardservice.dto.CardResponseDto;
import com.example.cardservice.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Kartice", description = "Upravljanje platnim karticama")
@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @Operation(summary = "Sve kartice")
    @GetMapping
    public ResponseEntity<List<CardResponseDto>> getAll() {
        return ResponseEntity.ok(cardService.getAll());
    }

    @Operation(summary = "Kartica po ID-u")
    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getById(id));
    }

    @Operation(summary = "Kartice za racun")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CardResponseDto>> getByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(cardService.getByAccountId(accountId));
    }

    @Operation(summary = "Izdaj karticu")
    @PostMapping
    public ResponseEntity<CardResponseDto> create(@Valid @RequestBody CardRequestDto dto) {
        return ResponseEntity.ok(cardService.create(dto));
    }

    @Operation(summary = "Izmeni karticu")
    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDto> update(@PathVariable Long id, @Valid @RequestBody CardRequestDto dto) {
        return ResponseEntity.ok(cardService.update(id, dto));
    }

    @Operation(summary = "Blokiraj karticu")
    @PutMapping("/{id}/block")
    public ResponseEntity<CardResponseDto> block(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.block(id));
    }

    @Operation(summary = "Odblokiraj karticu")
    @PutMapping("/{id}/unblock")
    public ResponseEntity<CardResponseDto> unblock(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.unblock(id));
    }

    @Operation(summary = "Obrisi karticu")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
