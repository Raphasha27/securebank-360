package com.banking.securebank360.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fraud_alerts")
public class FraudAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String ruleTriggered;

    @Column(nullable = false)
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(nullable = false)
    private String status; // NEW, INVESTIGATING, RESOLVED, DISMISSED

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    @Column(length = 1000)
    private String description;

    public FraudAlert() {}

    public FraudAlert(String transactionId, String accountId, String ruleTriggered,
                      String severity, String description) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.ruleTriggered = ruleTriggered;
        this.severity = severity;
        this.description = description;
        this.status = "NEW";
        this.detectedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public String getRuleTriggered() { return ruleTriggered; }
    public String getSeverity() { return severity; }
    public String getStatus() { return status; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public String getDescription() { return description; }

    public void setStatus(String status) { this.status = status; }
}
