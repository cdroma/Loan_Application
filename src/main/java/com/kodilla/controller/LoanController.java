package com.kodilla.controller;

import com.kodilla.domain.Loan;
import com.kodilla.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
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
        Loan loan = loanService.getLoanById(loanId);
        if (loan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
        }
        return loan;
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    @PutMapping("/{loanId}")
    public Loan updateLoan(@PathVariable Long loanId, @RequestBody Loan loanDetails) {
        try {
            return loanService.updateLoan(loanId, loanDetails);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
        }
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        try {
            loanService.deleteLoan(loanId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // no content response was added
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found");
        }
    }
}
