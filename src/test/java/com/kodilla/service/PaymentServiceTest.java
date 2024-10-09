package com.kodilla.service;

import com.kodilla.domain.Loan;
import com.kodilla.domain.Payment;
import com.kodilla.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    public PaymentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        // Given
        Loan loan = new Loan();
        loan.setId(1L);
        Payment payment = new Payment();
        payment.setAmount(100.0);
        payment.setPaymentDate(LocalDate.now());
        payment.setLoan(loan);

        when(paymentRepository.save(payment)).thenReturn(payment);

        // When
        Payment savedPayment = paymentService.createPayment(payment);

        // Then
        assertNotNull(savedPayment);
        assertEquals(100.0, savedPayment.getAmount());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testUpdatePayment() {
        // Given
        Loan loan = new Loan();
        loan.setId(1L);

        Payment existingPayment = new Payment();
        existingPayment.setId(1L);
        existingPayment.setAmount(100.0);
        existingPayment.setPaymentDate(LocalDate.of(2023, 10, 1));
        existingPayment.setLoan(loan);

        Payment updatedPaymentDetails = new Payment();
        updatedPaymentDetails.setAmount(200.0);
        updatedPaymentDetails.setPaymentDate(LocalDate.of(2024, 9, 12));

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(existingPayment));
        when(paymentRepository.save(existingPayment)).thenReturn(existingPayment);

        // When
        Payment updatedPayment = paymentService.updatePayment(1L, updatedPaymentDetails);

        // Then
        assertNotNull(updatedPayment);
        assertEquals(200.0, updatedPayment.getAmount());
        assertEquals(LocalDate.of(2024, 9, 12), updatedPayment.getPaymentDate());
        verify(paymentRepository, times(1)).findById(1L);
        verify(paymentRepository, times(1)).save(existingPayment);
    }

    @Test
    void testGetPaymentById() {
        // Given
        Loan loan = new Loan();
        loan.setId(1L);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100.0);
        payment.setPaymentDate(LocalDate.now());
        payment.setLoan(loan);

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        // When
        Payment foundPayment = paymentService.getPaymentById(1L);

        // Then
        assertNotNull(foundPayment);
        assertEquals(1L, foundPayment.getId());
        assertEquals(100.0, foundPayment.getAmount());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void testDeletePayment() {
        // Given
        Long paymentId = 1L;

        doNothing().when(paymentRepository).deleteById(paymentId);

        // When
        paymentService.deletePayment(paymentId);

        // Then
        verify(paymentRepository, times(1)).deleteById(paymentId);
    }

    @Test
    void testGetPaymentsByLoanId() {
        // Given
        Loan loan = new Loan();
        loan.setId(1L);

        Payment payment1 = new Payment();
        payment1.setId(1L);
        payment1.setAmount(100.0);
        payment1.setLoan(loan);

        Payment payment2 = new Payment();
        payment2.setId(2L);
        payment2.setAmount(200.0);
        payment2.setLoan(loan);

        List<Payment> payments = new ArrayList<>();
        payments.add(payment1);
        payments.add(payment2);

        when(paymentRepository.findByLoanId(1L)).thenReturn(payments);

        // When
        List<Payment> foundPayments = paymentService.
                getPaymentsByLoanId(1L);

        // Then
        assertEquals(2, foundPayments.size());
        assertEquals(100.0, foundPayments.get(0).getAmount());
        assertEquals(200.0, foundPayments.get(1).getAmount());
        verify(paymentRepository, times(1)).findByLoanId(1L);
    }
}