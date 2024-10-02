package com.kodilla.controller;


import com.kodilla.domain.Loan;
import com.kodilla.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/client/{clientId}")
    public List<Loan> getLoansByClient(@PathVariable Long clientId) {
        return loanService.getLoansByClientId(clientId);
    }

    @GetMapping("/currency/{currency}")
    public List<Loan> getLoansByCurrency(@PathVariable String currency) {
        return loanService.getLoansByCurrency(currency);
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.saveLoan(loan);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId) {
        Loan loan = loanService.getLoanById(loanId);
        if (loan != null) {
            return ResponseEntity.ok(loan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
