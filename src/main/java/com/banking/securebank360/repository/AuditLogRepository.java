package com.banking.securebank360.repository;

import com.banking.securebank360.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, String> {
    List<AuditLog> findByUserIdOrderByTimestampDesc(String userId);
}
