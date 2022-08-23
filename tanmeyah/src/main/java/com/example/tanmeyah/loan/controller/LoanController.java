package com.example.tanmeyah.loan.controller;

import com.example.tanmeyah.customer.service.CustomerService;
import com.example.tanmeyah.loan.LoanRequestBody;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.loan.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loan")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> addLoan(@RequestBody LoanRequestBody loanRequestBody) {
        return loanService.addLoan(loanRequestBody);
    }

    @GetMapping
    public ResponseEntity<?> getCustomer(@RequestBody String nationalId) {
        return customerService.getCustomerByNationalId(nationalId);
    }
}
