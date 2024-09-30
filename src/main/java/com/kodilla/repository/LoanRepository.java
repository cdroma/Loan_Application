package com.kodilla.repository;

import com.kodilla.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByClientId(Long clientId);
    List<Loan> findByCurrency(String currency);
}
