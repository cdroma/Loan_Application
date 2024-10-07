package com.kodilla.repository;

import com.kodilla.domain.CreditEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditEvaluationRepository extends JpaRepository<CreditEvaluation, Long> {
    CreditEvaluation findByClientId(Long clientId);
}