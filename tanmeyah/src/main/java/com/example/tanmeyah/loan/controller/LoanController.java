package com.example.tanmeyah.loan.controller;

import com.example.tanmeyah.customer.service.CustomerService;
import com.example.tanmeyah.loan.ConfirmLoanDTO;
import com.example.tanmeyah.loan.LoanDTO;
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
    public ResponseEntity<?> addLoan(@RequestBody LoanDTO loanRequestBody) {
        return loanService.addLoan(loanRequestBody);
    }
//@PostMapping("confirm")
//public  ResponseEntity<?> confirmLoan(@RequestBody String nationalId){
//     return    loanService.confirmLoan(nationalId);
//}
    @GetMapping
    public ResponseEntity<?> getCustomer(@RequestBody String nationalId) {
        return customerService.getCustomerByNationalId(nationalId);
    }
    @GetMapping("view")
    public ResponseEntity<?> viewActiveLoans(){
        return loanService.viewActiveLoans();
    }
    @PutMapping("revise")
    public ResponseEntity<?> confirmLoanRevision(@RequestBody ConfirmLoanDTO confirmLoanDTO){
        return loanService.confirmLoanRevision(confirmLoanDTO);
    }
}
