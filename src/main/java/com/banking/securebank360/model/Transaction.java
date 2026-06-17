package com.banking.securebank360.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String fromAccountId;

    @Column(nullable = false)
    private String toAccountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String type; // TRANSFER, DEPOSIT, WITHDRAWAL, PAYMENT

    @Column(nullable = false)
    private String status; // PENDING, COMPLETED, FAILED, REVERSED

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String description;
    private String referenceNumber;

    public Transaction() {}

    public Transaction(String fromAccountId, String toAccountId, BigDecimal amount,
                       String currency, String type, String description) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.description = description;
        this.status = "PENDING";
        this.timestamp = LocalDateTime.now();
        this.referenceNumber = "TXN-" + System.currentTimeMillis();
    }

    public String getId() { return id; }
    public String getFromAccountId() { return fromAccountId; }
    public String getToAccountId() { return toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
    public String getReferenceNumber() { return referenceNumber; }

    public void setStatus(String status) { this.status = status; }
}
