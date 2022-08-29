package com.example.tanmeyah.loan.repository;

import com.example.tanmeyah.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Long> {
}
