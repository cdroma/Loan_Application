package com.kodilla.domain;

import java.time.LocalDate;

public class Payment {

    private Long id;

    private double amount;
    private LocalDate paymentDate;

    private Loan loan;
}
