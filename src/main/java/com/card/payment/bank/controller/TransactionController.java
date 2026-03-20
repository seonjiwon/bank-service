package com.card.payment.bank.controller;

import com.card.payment.bank.entity.Transaction;
import com.card.payment.bank.repository.TransactionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionByDate(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<Transaction> transactions
            = transactionRepository.findByTransactionDateBetween(start, end);

        log.info("거래 내역 조회 - 날짜: {}, 건수: {}", date, transactions.size());

        return ResponseEntity.ok(transactions);

    }
}