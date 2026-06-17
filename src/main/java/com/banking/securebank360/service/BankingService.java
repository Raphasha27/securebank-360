package com.banking.securebank360.service;

import com.banking.securebank360.model.*;
import com.banking.securebank360.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankingService {

    private final CustomerRepository customerRepo;
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;
    private final FraudAlertRepository fraudRepo;
    private final AuditLogRepository auditRepo;

    public BankingService(CustomerRepository customerRepo, AccountRepository accountRepo,
                          TransactionRepository transactionRepo, FraudAlertRepository fraudRepo,
                          AuditLogRepository auditRepo) {
        this.customerRepo = customerRepo;
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
        this.fraudRepo = fraudRepo;
        this.auditRepo = auditRepo;
    }

    public Customer createCustomer(String firstName, String lastName, String email, String phone) {
        Customer customer = new Customer(firstName, lastName, email, phone);
        return customerRepo.save(customer);
    }

    public Account openAccount(String customerId, String accountType, BigDecimal initialDeposit, String currency) {
        if (initialDeposit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial deposit must be non-negative");
        }
        String accountNumber = "ACC-" + System.currentTimeMillis();
        Account account = new Account(accountNumber, accountType, initialDeposit, currency, customerId);
        return accountRepo.save(account);
    }

    @Transactional
    public Transaction transfer(String fromAccountId, String toAccountId, BigDecimal amount, String description) {
        Account from = accountRepo.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account to = accountRepo.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepo.save(from);
        accountRepo.save(to);

        Transaction txn = new Transaction(fromAccountId, toAccountId, amount, from.getCurrency(), "TRANSFER", description);
        txn.setStatus("COMPLETED");
        return transactionRepo.save(txn);
    }

    public List<Transaction> getTransactionHistory(String accountId) {
        return transactionRepo.findByFromAccountIdOrToAccountIdOrderByTimestampDesc(accountId, accountId);
    }

    public FraudAlert detectFraud(Transaction transaction) {
        // Rule: Large transactions
        if (transaction.getAmount().compareTo(new BigDecimal("100000")) > 0) {
            FraudAlert alert = new FraudAlert(
                    transaction.getId(), transaction.getFromAccountId(),
                    "LARGE_TRANSACTION", "HIGH",
                    "Large transaction detected: " + transaction.getAmount());
            return fraudRepo.save(alert);
        }
        return null;
    }

    public List<FraudAlert> getFraudAlerts(String status) {
        if (status != null) return fraudRepo.findByStatus(status);
        return fraudRepo.findAll();
    }

    public void logAudit(String userId, String action, String resource, String ip, String details) {
        auditRepo.save(new AuditLog(userId, action, resource, ip, details));
    }
}
