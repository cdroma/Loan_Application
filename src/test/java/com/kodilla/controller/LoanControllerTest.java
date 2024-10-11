package com.kodilla.controller;

import com.kodilla.domain.Loan;
import com.kodilla.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    private Loan loan1;
    private Loan loan2;

    @BeforeEach
    void setUp() {
        loan1 = new Loan();
        loan1.setId(1L);
        loan1.setAmount(1000.0);
        loan1.setCurrency("USD");
        loan1.setInterestRate(5.0);
        loan1.setStartDate(LocalDate.now());
        loan1.setDueDate(LocalDate.now().plusMonths(6));

        loan2 = new Loan();
        loan2.setId(2L);
        loan2.setAmount(2000.0);
        loan2.setCurrency("EUR");
        loan2.setInterestRate(3.5);
        loan2.setStartDate(LocalDate.now());
        loan2.setDueDate(LocalDate.now().plusMonths(12));
    }

    @Test
    void testGetLoansByClientId() throws Exception {
        // Given
        List<Loan> loans = Arrays.asList(loan1, loan2);
        Mockito.when(loanService.getLoansByClientId(1L)).thenReturn(loans);

        // When & Then
        mockMvc.perform(get("/api/loan/client/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(loans.size()))
                .andExpect(jsonPath("$[0].amount").value(1000.0))
                .andExpect(jsonPath("$[1].currency").value("EUR"));
    }

    @Test
    void testGetLoanById_Success() throws Exception {
        // Given
        Mockito.when(loanService.getLoanById(1L)).thenReturn(loan1);

        // When & Then
        mockMvc.perform(get("/api/loan/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void testGetLoanById_NotFound() throws Exception {
        // Given
        Mockito.when(loanService.getLoanById(anyLong())).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/loan/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateLoan() throws Exception {
        // Given
        Mockito.when(loanService.createLoan(any(Loan.class))).thenReturn(loan1);

        // When & Then
        mockMvc.perform(post("/api/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":1000.0,\"currency\":\"USD\",\"interestRate\":5.0,\"startDate\":\"2024-10-09\",\"dueDate\":\"2025-04-09\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.interestRate").value(5.0));
    }

    @Test
    void testUpdateLoan_Success() throws Exception {
        // Given
        Mockito.when(loanService.updateLoan(anyLong(), any(Loan.class))).thenReturn(loan1);

        // When & Then
        mockMvc.perform(put("/api/loan/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":1500.0,\"currency\":\"USD\",\"interestRate\":5.0,\"startDate\":\"2024-10-09\",\"dueDate\":\"2025-04-09\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000.0))  // Spodziewana wartość według danych testowych
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void testUpdateLoan_NotFound() throws Exception {
        // Given
        Mockito.when(loanService.updateLoan(anyLong(), any(Loan.class)))
                .thenThrow(new RuntimeException("Loan not found"));

        // When & Then
        mockMvc.perform(put("/api/loan/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":1500.0,\"currency\":\"USD\",\"interestRate\":5.0,\"startDate\":\"2024-10-09\",\"dueDate\":\"2025-04-09\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteLoan_Success() throws Exception {
        // Given
        Mockito.doNothing().when(loanService).deleteLoan(1L);

        // When & Then
        mockMvc.perform(delete("/api/loan/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteLoan_NotFound() throws Exception {
        // Given
        Mockito.doThrow(new RuntimeException("Loan not found")).when(loanService).deleteLoan(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/loan/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}