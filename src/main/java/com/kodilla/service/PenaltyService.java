package com.kodilla.service;

import com.kodilla.domain.Penalty;
import com.kodilla.repository.PenaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;

    @Autowired
    public PenaltyService(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }

    public List<Penalty> getPenaltiesByLoanId(Long loanId) {
        return penaltyRepository.findByLoanId(loanId);
    }

    public Penalty createPenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }
}