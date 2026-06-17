package com.banking.securebank360.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String resource;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String ipAddress;
    private String details;

    public AuditLog() {}

    public AuditLog(String userId, String action, String resource, String ipAddress, String details) {
        this.userId = userId;
        this.action = action;
        this.resource = resource;
        this.ipAddress = ipAddress;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getAction() { return action; }
    public String getResource() { return resource; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getIpAddress() { return ipAddress; }
    public String getDetails() { return details; }
}
