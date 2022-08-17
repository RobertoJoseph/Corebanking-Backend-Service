package com.example.tanmeyah.loan.service;

import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.repository.EmployeeRepository;
import com.example.tanmeyah.exception.EmployeeNotFoundInBranch;
import com.example.tanmeyah.exception.NotFoundException;
import com.example.tanmeyah.facility.Facility;
import com.example.tanmeyah.facility.FacilityRepository;
import com.example.tanmeyah.loan.LoanRepository;
import com.example.tanmeyah.loan.LoanRequestBody;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.product.Product;
import com.example.tanmeyah.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final FacilityRepository facilityRepository;
    private final LoanRepository loanRepository;

    //TODO CHECK VALIDATIONS
    public ResponseEntity<?> addLoan(LoanRequestBody loanRequestBody) {
        Optional<Product> product = Optional.ofNullable(productRepository.findById(loanRequestBody.getProductId())
            .orElseThrow(() -> new NotFoundException(String.format("Product with %s id not found", loanRequestBody.getProductId()))));

        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(loanRequestBody.getCustomerId())
            .orElseThrow(() -> new NotFoundException(String.format("Customer with %s id not found", loanRequestBody.getCustomerId()))));

        Optional<Facility> facility = Optional.ofNullable(facilityRepository.findById(loanRequestBody.getFacilityId())
            .orElseThrow(() -> new NotFoundException(String.format("Facility with %s id not found", loanRequestBody.getFacilityId()))));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<String> BAD_REQUEST = validateRequestedLoan(customer);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        Loan loan = new Loan(product.get(), customer.get(), facility.get(), customer.get().getRequestedAmount(), loanRequestBody.getRepayments());
        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);
        employee.get().addLoanToLoanOfficer(loan);
        customer.get().addLoanToGrantedCustomer(loan);

        employeeRepository.save(employee.get());
        return ResponseEntity.status(HttpStatus.OK).body(loan);

    }

    private ResponseEntity<String> validateRequestedLoan(Optional<Customer> customer) {
        if (!customer.get().isCommissionPaid() )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to pay the commission");

        if (!customer.get().getCommissionPaidDate().plusMonths(3L).isAfter(LocalDate.now()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Expired");

        if (customer.get().getRequestedAmount() < customer.get().getProduct().getProductType().getMin())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested amount is less than the min");

        if (customer.get().getRequestedAmount() > customer.get().getProduct().getProductType().getMax())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested amount is more than than the max");
        return null;
    }
}

