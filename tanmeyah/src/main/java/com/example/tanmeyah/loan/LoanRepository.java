package com.example.tanmeyah.loan;

import com.example.tanmeyah.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {
}
