package com.kodilla.service;

import com.kodilla.domain.Loan;
import com.kodilla.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long loanId, Loan loanDetails) {
        Loan existingLoan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));
        existingLoan.setAmount(loanDetails.getAmount());
        existingLoan.setCurrency(loanDetails.getCurrency());
        return loanRepository.save(existingLoan);
    }

    public void deleteLoan(Long loanId) {
        loanRepository.deleteById(loanId);
    }

    public List<Loan> getLoansByClientId(Long clientId) {
        return loanRepository.findByClientId(clientId);
    }

    public List<Loan> getLoansByCurrency(String currency) {
        return loanRepository.findByCurrency(currency);
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public List<Loan> getAllLoans() {return loanRepository.findAll();}
}