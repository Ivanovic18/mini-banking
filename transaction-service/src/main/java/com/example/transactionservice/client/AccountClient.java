package com.example.transactionservice.client;

import com.example.transactionservice.dto.AccountDto;
import com.example.transactionservice.dto.AmountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/accounts/{id}")
    AccountDto getAccountById(@PathVariable Long id);

    @PostMapping("/api/accounts/{id}/deposit")
    AccountDto deposit(@PathVariable Long id, @RequestBody AmountDto amount);

    @PostMapping("/api/accounts/{id}/withdraw")
    AccountDto withdraw(@PathVariable Long id, @RequestBody AmountDto amount);
}
