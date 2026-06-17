package com.banking.securebank360.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private boolean kycVerified;
    private String riskCategory;

    public Customer() {}

    public Customer(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
        this.kycVerified = false;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isKycVerified() { return kycVerified; }
    public String getRiskCategory() { return riskCategory; }

    public void setKycVerified(boolean kycVerified) { this.kycVerified = kycVerified; }
    public void setRiskCategory(String riskCategory) { this.riskCategory = riskCategory; }
}
