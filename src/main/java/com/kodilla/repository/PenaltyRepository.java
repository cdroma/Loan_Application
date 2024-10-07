package com.kodilla.repository;

import com.kodilla.domain.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    List<Penalty> findByLoanId(Long loanId);
}
