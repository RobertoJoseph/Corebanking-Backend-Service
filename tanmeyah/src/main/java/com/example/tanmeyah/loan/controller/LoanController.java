package com.example.tanmeyah.loan.controller;

import com.example.tanmeyah.loan.LoanRequestBody;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.loan.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loan")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<?> addLoan(@RequestBody LoanRequestBody loanRequestBody) {
        return loanService.addLoan(loanRequestBody);
    }
}
