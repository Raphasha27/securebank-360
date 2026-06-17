package com.banking.securebank360.service;

import com.banking.securebank360.model.*;
import com.banking.securebank360.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankingServiceTest {

    @Mock private CustomerRepository customerRepo;
    @Mock private AccountRepository accountRepo;
    @Mock private TransactionRepository transactionRepo;
    @Mock private FraudAlertRepository fraudRepo;
    @Mock private AuditLogRepository auditRepo;

    private BankingService bankingService;

    @BeforeEach
    void setUp() {
        bankingService = new BankingService(customerRepo, accountRepo, transactionRepo, fraudRepo, auditRepo);
    }

    @Test
    void createCustomer_ShouldSaveAndReturn() {
        when(customerRepo.save(any())).thenAnswer(i -> i.getArgument(0));
        Customer result = bankingService.createCustomer("John", "Doe", "john@test.com", "+27123456789");
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(customerRepo).save(any());
    }

    @Test
    void transfer_InsufficientFunds_ShouldThrow() {
        Account from = new Account("ACC-001", "SAVINGS", BigDecimal.ZERO, "ZAR", "CUST-1");
        Account to = new Account("ACC-002", "SAVINGS", BigDecimal.valueOf(100), "ZAR", "CUST-2");

        when(accountRepo.findById("ACC-001")).thenReturn(Optional.of(from));
        when(accountRepo.findById("ACC-002")).thenReturn(Optional.of(to));

        assertThrows(RuntimeException.class, () ->
            bankingService.transfer("ACC-001", "ACC-002", BigDecimal.valueOf(50), "Test transfer"));
    }
}
