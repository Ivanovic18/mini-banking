package com.example.transactionservice.controller;

import com.example.transactionservice.dto.TransactionDetailsDto;
import com.example.transactionservice.dto.TransactionRequestDto;
import com.example.transactionservice.dto.TransactionResponseDto;
import com.example.transactionservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transakcije", description = "Upravljanje transakcijama sa Resilience4j zastitom")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Sve transakcije")
    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @Operation(summary = "Transakcija po ID-u")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @Operation(summary = "Detalji transakcije sa podacima o racunu")
    @GetMapping("/{id}/details")
    public ResponseEntity<TransactionDetailsDto> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getDetails(id));
    }

    @Operation(summary = "Transakcije za racun")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDto>> getByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getByAccountId(accountId));
    }

    @Operation(summary = "Kreiraj transakciju (uplata/isplata)")
    @PostMapping
    public ResponseEntity<TransactionResponseDto> create(@Valid @RequestBody TransactionRequestDto dto) {
        return ResponseEntity.ok(transactionService.create(dto));
    }

    @Operation(summary = "Obrisi transakciju")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
