package com.example.accountservice.client;

import com.example.accountservice.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "transaction-service")
public interface TransactionClient {

    @GetMapping("/api/transactions/account/{accountId}")
    List<TransactionDto> getTransactionsByAccountId(@PathVariable Long accountId);
}
