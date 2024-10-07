package com.kodilla.controller;

import com.kodilla.domain.Penalty;
import com.kodilla.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penalty")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @Autowired
    public PenaltyController(PenaltyService penaltyService) {
        this.penaltyService = penaltyService;
    }

    @GetMapping("/loan/{loanId}")
    public List<Penalty> getPenaltiesByLoanId(@PathVariable Long loanId) {
        return penaltyService.getPenaltiesByLoanId(loanId);
    }

    @PostMapping("/loan/{loanId}")
    public Penalty createPenalty(@RequestBody Penalty penalty) {
        return penaltyService.createPenalty(penalty);
    }
}