package com.banking.securebank360.controller;

import com.banking.securebank360.model.*;
import com.banking.securebank360.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Map<String, String> req) {
        Customer customer = bankingService.createCustomer(
                req.get("firstName"), req.get("lastName"),
                req.get("email"), req.get("phone"));
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> openAccount(@RequestBody Map<String, Object> req) {
        Account account = bankingService.openAccount(
                (String) req.get("customerId"),
                (String) req.get("accountType"),
                new BigDecimal(req.get("initialDeposit").toString()),
                (String) req.get("currency"));
        return ResponseEntity.ok(account);
    }

    @PostMapping("/transactions/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> req) {
        try {
            Transaction txn = bankingService.transfer(
                    (String) req.get("fromAccountId"),
                    (String) req.get("toAccountId"),
                    new BigDecimal(req.get("amount").toString()),
                    (String) req.get("description"));
            bankingService.detectFraud(txn);
            return ResponseEntity.ok(txn);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String id) {
        return ResponseEntity.ok(bankingService.getTransactionHistory(id));
    }

    @GetMapping("/fraud-alerts")
    public ResponseEntity<List<FraudAlert>> getFraudAlerts(
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(bankingService.getFraudAlerts(status));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "securebank-360"));
    }
}
