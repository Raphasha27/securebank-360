package com.banking.securebank360.repository;

import com.banking.securebank360.model.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudAlertRepository extends JpaRepository<FraudAlert, String> {
    List<FraudAlert> findByStatus(String status);
    List<FraudAlert> findBySeverity(String severity);
    List<FraudAlert> findByAccountIdOrderByDetectedAtDesc(String accountId);
}
