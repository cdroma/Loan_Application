package com.kodilla.scheduler;

import com.kodilla.domain.Loan;
import com.kodilla.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class LateFeeSchedulerTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LateFeeScheduler lateFeeScheduler;

    @Test
    void testApplyLateFees() {

        MockitoAnnotations.openMocks(this);

        // Given
        Loan overdueLoan = new Loan();
        overdueLoan.setId(1L);
        overdueLoan.setAmount(1000.0);
        overdueLoan.setPaidOff(false);
        overdueLoan.setDueDate(LocalDate.now().minusDays(5)); // overdue

        Loan notOverdueLoan = new Loan();
        notOverdueLoan.setId(2L);
        notOverdueLoan.setAmount(500.0);
        notOverdueLoan.setPaidOff(false);
        notOverdueLoan.setDueDate(LocalDate.now().plusDays(10)); // not overdue

        Loan paidOffLoan = new Loan();
        paidOffLoan.setId(3L);
        paidOffLoan.setAmount(800.0);
        paidOffLoan.setPaidOff(true);
        paidOffLoan.setDueDate(LocalDate.now().minusDays(15)); // paid off, no fee

        List<Loan> loans = Arrays.asList(overdueLoan, notOverdueLoan, paidOffLoan);

        when(loanRepository.findAll()).thenReturn(loans);

        // When
        lateFeeScheduler.applyLateFees();

        // Then
        // Verifying that only overdue loan without paid-off status was modified and saved//
        verify(loanRepository, times(1)).save(overdueLoan);
        assert(overdueLoan.getAmount() == 1000.0 * 1.02);

        // Verifying that other loans were not modified & saved//
        verify(loanRepository, never()).save(notOverdueLoan);
        verify(loanRepository, never()).save(paidOffLoan);
    }
}