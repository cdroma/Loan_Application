package com.kodilla.domain;

import java.time.LocalDate;
import java.util.List;

public class Loan {

    private Long id;

    private double amount;
    private String currency;
    private double interestRate;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean isPaidOff;

    private Client client;

    private List<Payment> payments;
}
