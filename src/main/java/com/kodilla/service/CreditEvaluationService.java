package com.kodilla.service;

import com.kodilla.domain.CreditEvaluation;
import com.kodilla.repository.CreditEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditEvaluationService {

    private final CreditEvaluationRepository creditEvaluationRepository;

    @Autowired
    public CreditEvaluationService(CreditEvaluationRepository creditEvaluationRepository) {
        this.creditEvaluationRepository = creditEvaluationRepository;
    }

    // Create or save
    public CreditEvaluation createCreditEvaluation(CreditEvaluation creditEvaluation) {
        return creditEvaluationRepository.save(creditEvaluation);
    }

    // Get all
    public List<CreditEvaluation> getAllCreditEvaluations() {
        return creditEvaluationRepository.findAll();
    }

    // Get
    public Optional<CreditEvaluation> getCreditEvaluationById(Long id) {
        return creditEvaluationRepository.findById(id);
    }

    // Update
    public CreditEvaluation updateCreditEvaluation(Long id, CreditEvaluation updatedCreditEvaluation) {
        CreditEvaluation existingCreditEvaluation = creditEvaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CreditEvaluation not found with id: " + id));

        existingCreditEvaluation.setClientId(updatedCreditEvaluation.getClientId());
        existingCreditEvaluation.setCreditScore(updatedCreditEvaluation.getCreditScore());

        return creditEvaluationRepository.save(existingCreditEvaluation);
    }

    // Delete
    public void deleteCreditEvaluation(Long id) {
        if (!creditEvaluationRepository.existsById(id)) {
            throw new RuntimeException("CreditEvaluation not found with id: " + id);
        }
        creditEvaluationRepository.deleteById(id);
    }
}
