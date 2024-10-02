package com.kodilla.scheduler;

import com.kodilla.domain.Loan;
import com.kodilla.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LateFeeScheduler {

    @Autowired
    private LoanRepository loanRepository;

    @Scheduled(cron = "0 0 0 * * ?") // every day at midnight//
    public void applyLateFees() {
        List<Loan> overdueLoans = loanRepository.findAll(); // can add criteria//
        overdueLoans.stream()
                .filter(loan -> !loan.isPaidOff() && loan.getDueDate().isBefore(LocalDate.now()))
                .forEach(loan -> {
                    loan.setAmount(loan.getAmount() * 1.02); // 2% late fee percentage//
                    loanRepository.save(loan);
                });
    }
}
