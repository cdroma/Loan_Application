package com.kodilla.controller;


import com.kodilla.domain.Loan;
import com.kodilla.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/client/{clientId}")
    public List<Loan> getLoansByClientId(@PathVariable Long clientId) {
        return loanService.getLoansByClientId(clientId);
    }

    @GetMapping("/{loanId}")
    public Loan getLoanById(@PathVariable Long loanId) {
        return loanService.getLoanById(loanId);
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    @PutMapping("/{loanId}")
    public Loan updateLoan(@PathVariable Long loanId, @RequestBody Loan loanDetails) {
        return loanService.updateLoan(loanId, loanDetails);
    }

    @DeleteMapping("/{loanId}")
    public void deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
    }
}
