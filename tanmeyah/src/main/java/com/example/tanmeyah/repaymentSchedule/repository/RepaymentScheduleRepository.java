package com.example.tanmeyah.repaymentSchedule.repository;

import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.repaymentSchedule.domain.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule,Long> {
}
