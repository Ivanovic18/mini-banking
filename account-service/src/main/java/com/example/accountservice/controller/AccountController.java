package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountOverviewDto;
import com.example.accountservice.dto.AccountRequestDto;
import com.example.accountservice.dto.AccountResponseDto;
import com.example.accountservice.dto.AmountRequestDto;
import com.example.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Racuni", description = "Upravljanje bankovnim racunima")
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Svi racuni")
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @Operation(summary = "Racun po ID-u")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @Operation(summary = "Pregled racuna sa vlasnikom, karticama i transakcijama")
    @GetMapping("/{id}/overview")
    public ResponseEntity<AccountOverviewDto> getOverview(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getOverview(id));
    }

    @Operation(summary = "Kreiraj racun")
    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@Valid @RequestBody AccountRequestDto dto) {
        return ResponseEntity.ok(accountService.create(dto));
    }

    @Operation(summary = "Izmeni racun")
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> update(@PathVariable Long id, @Valid @RequestBody AccountRequestDto dto) {
        return ResponseEntity.ok(accountService.update(id, dto));
    }

    @Operation(summary = "Uplata na racun")
    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponseDto> deposit(@PathVariable Long id, @Valid @RequestBody AmountRequestDto dto) {
        return ResponseEntity.ok(accountService.deposit(id, dto.getIznos()));
    }

    @Operation(summary = "Isplata sa racuna")
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponseDto> withdraw(@PathVariable Long id, @Valid @RequestBody AmountRequestDto dto) {
        return ResponseEntity.ok(accountService.withdraw(id, dto.getIznos()));
    }

    @Operation(summary = "Obrisi racun")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
