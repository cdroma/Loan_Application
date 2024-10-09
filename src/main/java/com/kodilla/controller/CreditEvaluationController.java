package com.kodilla.controller;

import com.kodilla.domain.CreditEvaluation;
import com.kodilla.service.CreditEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-evaluations")
public class CreditEvaluationController {

    private final CreditEvaluationService creditEvaluationService;

    @Autowired
    public CreditEvaluationController(CreditEvaluationService creditEvaluationService) {
        this.creditEvaluationService = creditEvaluationService;
    }

    @PostMapping
    public CreditEvaluation createCreditEvaluation(@RequestBody CreditEvaluation creditEvaluation) {
        return creditEvaluationService.createCreditEvaluation(creditEvaluation);
    }

    @GetMapping
    public List<CreditEvaluation> getAllCreditEvaluations() {
        return creditEvaluationService.getAllCreditEvaluations();
    }

    @GetMapping("/{id}")
    public Optional<CreditEvaluation> getCreditEvaluationById(@PathVariable Long id) {
        return creditEvaluationService.getCreditEvaluationById(id);
    }

    @PutMapping("/{id}")
    public CreditEvaluation updateCreditEvaluation(@PathVariable Long id, @RequestBody CreditEvaluation creditEvaluation) {
        return creditEvaluationService.updateCreditEvaluation(id, creditEvaluation);
    }

    @DeleteMapping("/{id}")
    public void deleteCreditEvaluation(@PathVariable Long id) {
        creditEvaluationService.deleteCreditEvaluation(id);
    }
}
