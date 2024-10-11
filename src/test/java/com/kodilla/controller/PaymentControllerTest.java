package com.kodilla.controller;

import com.kodilla.domain.Payment;
import com.kodilla.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void testGetPaymentsByLoanId() throws Exception {
        // Given
        Payment payment1 = new Payment();
        payment1.setId(1L);
        payment1.setAmount(100.00);
        payment1.setPaymentDate(LocalDate.now());

        Payment payment2 = new Payment();
        payment2.setId(2L);
        payment2.setAmount(200.00);
        payment2.setPaymentDate(LocalDate.now().minusDays(1));

        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(paymentService.getPaymentsByLoanId(1L)).thenReturn(payments);

        // When & Then
        mockMvc.perform(get("/api/payment/loan/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].amount").value(100.00))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].amount").value(200.00));
    }

    @Test
    void testGetPaymentById() throws Exception {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100.00);
        payment.setPaymentDate(LocalDate.now());

        when(paymentService.getPaymentById(1L)).thenReturn(payment);

        // When & Then
        mockMvc.perform(get("/api/payment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    void testCreatePayment() throws Exception {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(150.00);
        payment.setPaymentDate(LocalDate.now());

        when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

        String paymentJson = "{\"amount\":150.00,\"paymentDate\":\"" + LocalDate.now() + "\"}";

        // When & Then
        mockMvc.perform(post("/api/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(150.00));
    }

    @Test
    void testUpdatePayment() throws Exception {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(200.00);
        payment.setPaymentDate(LocalDate.now());

        when(paymentService.updatePayment(eq(1L), any(Payment.class))).thenReturn(payment);

        String paymentJson = "{\"amount\":200.00,\"paymentDate\":\"" + LocalDate.now() + "\"}";

        // When & Then
        mockMvc.perform(put("/api/payment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(200.00));
    }

    @Test
    void testDeletePayment() throws Exception {
        // Given
        doNothing().when(paymentService).deletePayment(1L);

        // When & Then
        mockMvc.perform(delete("/api/payment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(paymentService, times(1)).deletePayment(1L);
    }
}