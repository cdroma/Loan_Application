package com.kodilla.controller;

import com.kodilla.domain.Payment;
import com.kodilla.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/loan/{loanId}")
    public List<Payment> getPaymentsByLoanId(@PathVariable Long loanId) {
        return paymentService.getPaymentsByLoanId(loanId);
    }

    @GetMapping("/{paymentId}")
    public Payment getPaymentById(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PutMapping("/{paymentId}")
    public Payment updatePayment(@PathVariable Long paymentId, @RequestBody Payment paymentDetails) {
        return paymentService.updatePayment(paymentId, paymentDetails);
    }

    @DeleteMapping("/{paymentId}")
    public void deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
    }
}
