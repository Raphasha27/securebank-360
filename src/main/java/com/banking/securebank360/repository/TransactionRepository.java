package com.banking.securebank360.repository;

import com.banking.securebank360.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByFromAccountIdOrToAccountIdOrderByTimestampDesc(String fromId, String toId);
    List<Transaction> findByStatus(String status);
}
