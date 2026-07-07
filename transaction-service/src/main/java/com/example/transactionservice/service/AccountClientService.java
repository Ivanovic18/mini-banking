package com.example.transactionservice.service;

import com.example.transactionservice.client.AccountClient;
import com.example.transactionservice.dto.AmountDto;
import com.example.transactionservice.entity.TipTransakcije;
import com.example.transactionservice.exception.ServiceUnavailableException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountClientService {

    private final AccountClient accountClient;

    @Retry(name = "accountService")
    @CircuitBreaker(name = "accountService", fallbackMethod = "promeniStanjeFallback")
    public IshodPromene promeniStanje(Long accountId, TipTransakcije tipTransakcije, BigDecimal iznos) {
        AmountDto amountDto = new AmountDto(iznos);
        try {
            if (tipTransakcije == TipTransakcije.UPLATA) {
                accountClient.deposit(accountId, amountDto);
            } else {
                accountClient.withdraw(accountId, amountDto);
            }
            return IshodPromene.USPESNO;
        } catch (FeignException.NotFound e) {
            return IshodPromene.RACUN_NE_POSTOJI;
        } catch (FeignException.BadRequest e) {
            return IshodPromene.NEDOVOLJNO_SREDSTAVA;
        }
    }

    public IshodPromene promeniStanjeFallback(Long accountId, TipTransakcije tipTransakcije, BigDecimal iznosm, Throwable t) {
        throw new ServiceUnavailableException("Account servis je trenutno nedostupan, pokusajte kasnije");
    }
}
