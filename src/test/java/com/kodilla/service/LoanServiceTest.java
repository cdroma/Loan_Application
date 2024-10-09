package com.kodilla.service;

import com.kodilla.domain.Client;
import com.kodilla.domain.Loan;
import com.kodilla.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LoanServiceTest {
    @MockBean
    private LoanRepository loanRepository;

    @Autowired
    private LoanService loanService;

    @Test
    void testCreateLoan() {
        // Given
        Client client = new Client(1L, "John", "Doe", "john.doe@example.com", "123456789");
        Loan loan = new Loan(1000.0, "USD", 5.0, LocalDate.now(), LocalDate.now().plusMonths(6), client);

        when(loanRepository.save(loan)).thenReturn(loan);

        // When
        Loan savedLoan = loanService.createLoan(loan);

        // Then
        assertNotNull(savedLoan);
        assertEquals(1000.0, savedLoan.getAmount());
        assertEquals("USD", savedLoan.getCurrency());
        assertEquals(5.0, savedLoan.getInterestRate());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void testGetLoanById() {
        // Given
        Loan loan = new Loan(1000.0, "USD", 5.0, LocalDate.now(), LocalDate.now().plusMonths(6), new Client());
        loan.setId(1L);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        // When
        Loan foundLoan = loanService.getLoanById(1L);

        // Then
        assertNotNull(foundLoan);
        assertEquals(1L, foundLoan.getId());
        verify(loanRepository, times(1)).findById(1L);
    }
}