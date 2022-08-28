package com.example.tanmeyah.loan;

import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.loan.domain.LoanId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Long> {
    Optional<Loan> findLoanByLoanId(LoanId loanId);
}
