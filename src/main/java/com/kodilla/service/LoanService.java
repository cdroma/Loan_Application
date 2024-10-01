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

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
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

}
