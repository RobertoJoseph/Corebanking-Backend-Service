package com.example.tanmeyah.loan.service;

import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.repository.EmployeeRepository;
import com.example.tanmeyah.exception.NotFoundException;
import com.example.tanmeyah.facility.Facility;
import com.example.tanmeyah.facility.FacilityRepository;
import com.example.tanmeyah.loan.ConfirmLoanDTO;
import com.example.tanmeyah.loan.LoanRepository;
import com.example.tanmeyah.loan.LoanDTO;
import com.example.tanmeyah.loan.constant.Status;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.loan.domain.LoanId;
import com.example.tanmeyah.product.Product;
import com.example.tanmeyah.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
public class LoanService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final FacilityRepository facilityRepository;
    private final LoanRepository loanRepository;

    public ResponseEntity<?> addLoan(LoanDTO loanRequestBody) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findCustomerByNationalId(loanRequestBody.getCustomerNationalId())
            .orElseThrow(() -> new NotFoundException(String.format("Customer with %s id not found", loanRequestBody.getCustomerNationalId()))));

        ResponseEntity<String> BAD_REQUEST = validateRequestedLoan(customer, loanRequestBody);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        //TODO if he has facility, okay, if not make a facilty for him
        Facility facility = null;
        if (!facilityRepository.findFacilityByFacilityName(loanRequestBody.getFacilityName()).isPresent()) {
            facility = new Facility(loanRequestBody.getFacilityName(), customer.get());
        } else {
            facility = facilityRepository.findFacilityByFacilityName(loanRequestBody.getFacilityName()).get();
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Employee> loanOfficer = employeeRepository.findEmployeeByEmail(email);
        Loan loan = new Loan(
            customer.get().getProduct(),
            customer.get(),
            facility,
            loanRequestBody.getAmount(),
            loanRequestBody.getRepayments(),
            Status.ONE
        );

        if (loanRequestBody.getProductType().isGranted())
            customer.get().setGrantorNationalId(loanRequestBody.getGrantorNationalId());
        customerRepository.findCustomerByNationalId(loanRequestBody.getCustomerNationalId())
            .get().addLoanToGrantedCustomer(loan);
        loanOfficer.get().addLoanToLoanOfficer(loan);
        employeeRepository.save(loanOfficer.get());

        return ResponseEntity.status(OK).body("You can complete the steps to the next window PLEASE!");
    }

    private ResponseEntity<String> validateRequestedLoan(Optional<Customer> customer, LoanDTO loanDTO) {

        if (!customer.get().getProduct().getProductType().name().equals(loanDTO.getProductType().name()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to choose the same product you requested before");

        if (!customer.get().isCommissionPaid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to pay the commission");

        if (!customer.get().getCommissionPaidDate().plusMonths(3L).isAfter(LocalDate.now()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Expired");

        if (loanDTO.getAmount() < loanDTO.getProductType().getMin())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested amount is less than the min");

        if (loanDTO.getAmount() > loanDTO.getProductType().getMax())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested amount is more than than the max");
        if (loanDTO.getProductType().isGranted() == true) {
            if (customerRepository.findCustomerByNationalId(loanDTO.getGrantorNationalId()).isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You need a grantor for this product");
        }


        return null;
    }

    //    public ResponseEntity<?> confirmLoan(String nationalId) {
//
//        try {
//            Optional<Customer> customerOptional = customerRepository.findCustomerByNationalId(nationalId);
//            customerOptional.orElseThrow(() -> new RuntimeException("Cannot find Customer"));
//            Loan loan = new Loan(
//                customerOptional.get().getProduct(),
//                customerOptional.get(),
//                customerOptional.get().getFacility(),
//                customerOptional.get().getRequestedAmount(),
//                customerOptional.get().getNumberOfRepayments(),
//                    Status.ONE
//            );
//            if (customerOptional.get().getProduct().getProductType().isGranted()) {
//                try {
//                    Optional<Customer> customerByNationalId = customerRepository
//                        .findCustomerByNationalId(customerOptional.get().getGrantorNationalId());
//                    customerByNationalId.ifPresentOrElse(grantor -> {
//                        grantor.addLoanToGrantedCustomer(loan);
//                    }, () -> {
//                        throw new RuntimeException("Enter Correct national id of grantor");
//                    });
//                } catch (RuntimeException e) {
//                    return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//                }
//            }
//            Long id = customerOptional.get().getLoanOfficerId();
//            Optional<Employee> loanOptional = employeeRepository.findById(id);
//            loanOptional.get().addLoanToLoanOfficer(loan);
//            employeeRepository.save(loanOptional.get());
//            return ResponseEntity.status(HttpStatus.OK).body(loan);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//        }
//
//
//    }
    public ResponseEntity<?> viewActiveLoans() {
        List<Loan> loans = new ArrayList<>();
        loans = loanRepository.findAll().stream().filter(loan -> loan.getStatus().equals(Status.ONE)).collect(Collectors.toList());
        return ResponseEntity.status(OK).body(loans);
    }

    public ResponseEntity<?> confirmLoanRevision(ConfirmLoanDTO confirmLoanDTO) {
        Optional<Loan> loanOptional = findLoan(confirmLoanDTO);
        if (!loanOptional.isPresent())
            return ResponseEntity.status(OK).body("Cannot find Loan");

        loanOptional.get().setStatus(Status.TWO);
        return ResponseEntity.status(OK).body("Loan Revised Proceed to manager");
    }

    public ResponseEntity<?> confirmLoan(ConfirmLoanDTO confirmLoanDTO) {
        Optional<Loan> loanOptional = findLoan(confirmLoanDTO);
        if (!loanOptional.isPresent())
            return ResponseEntity.status(OK).body("Cannot find Loan");

        loanOptional.get().setStatus(Status.THREE);
        return ResponseEntity.status(OK).body("Loan Confirmed");
    }

    private Optional<Loan> findLoan(ConfirmLoanDTO confirmLoanDTO) {
        LoanId loanId = new LoanId();
        loanId.setCustomerId(confirmLoanDTO.getCustomerId());
        loanId.setProductId(confirmLoanDTO.getProductId());
        Optional<Loan> loanOptional = loanRepository.findLoanByLoanId(loanId);
        return loanOptional;
    }

    public ResponseEntity<?> viewLoansOfBranch() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Employee> manager = employeeRepository.findEmployeeByEmail(email);
        List<Employee> loanOfficers = manager.get().getBranch().getEmployeeList().stream()
            .filter(employee -> employee.getRole().name().equals("LOAN_OFFICER"))
            .collect(Collectors.toList());

        List<Loan> loans = new ArrayList<>();
        for (Employee e : loanOfficers) {
            for (Loan l : e.getLoansOfLoanOfficer()) {
                if (l.getStatus().name().equals("TWO"))
                    loans.add(l);
            }
        }
        return ResponseEntity.status(OK).body(loans);
    }

}

