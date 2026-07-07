package com.example.accountservice.client;

import com.example.accountservice.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "card-service")
public interface CardClient {

    @GetMapping("/api/cards/account/{accountId}")
    List<CardDto> getCardsByAccountId(@PathVariable Long accountId);
}
