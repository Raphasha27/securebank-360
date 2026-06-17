package com.banking.securebank360.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType; // SAVINGS, CURRENT

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String customerId;

    private boolean active;
    private LocalDateTime openedAt;

    public Account() {}

    public Account(String accountNumber, String accountType, BigDecimal balance,
                   String currency, String customerId) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
        this.customerId = customerId;
        this.active = true;
        this.openedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public String getCustomerId() { return customerId; }
    public boolean isActive() { return active; }
    public LocalDateTime getOpenedAt() { return openedAt; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setActive(boolean active) { this.active = active; }
}
