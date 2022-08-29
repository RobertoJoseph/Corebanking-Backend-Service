package com.example.tanmeyah.loan.service;

import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.repository.EmployeeRepository;
import com.example.tanmeyah.exception.NotFoundException;
import com.example.tanmeyah.facility.Facility;
import com.example.tanmeyah.facility.FacilityRepository;
import com.example.tanmeyah.loan.repository.LoanRepository;
import com.example.tanmeyah.loan.LoanDTO;
import com.example.tanmeyah.loan.constant.Status;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.product.domain.Product;
import com.example.tanmeyah.product.repository.ProductRepository;
import com.example.tanmeyah.repaymentSchedule.domain.RepaymentSchedule;
import com.example.tanmeyah.repaymentSchedule.dto.RepaymentScheduleDTO;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.Opt;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
public class LoanService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final FacilityRepository facilityRepository;
    private final LoanRepository loanRepository;
    private final ModelMapper mapper;

    public ResponseEntity<?> addLoan(LoanDTO loanDTO) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findCustomerByNationalId(loanDTO.getCustomerNationalId())
                .orElseThrow(() -> new NotFoundException(String.format("Customer with %s id not found", loanDTO.getCustomerNationalId()))));
        Optional<Product> productOptional = productRepository.findById(loanDTO.getProductId());
        if (!productOptional.isPresent())
            return ResponseEntity.status(BAD_REQUEST).body("Incorrect product ID!");

        ResponseEntity<String> BAD_REQUEST = validateRequestedLoan(customer, loanDTO, productOptional.get());
        if (BAD_REQUEST != null) return BAD_REQUEST;
        //TODO if he has facility, okay, if not make a facilty for him
        Facility facility = null;
        if (!facilityRepository.findFacilityByFacilityName(loanDTO.getFacilityName()).isPresent()) {
            facility = new Facility(loanDTO.getFacilityName(), customer.get());
        } else {
            facility = facilityRepository.findFacilityByFacilityName(loanDTO.getFacilityName()).get();
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Employee> loanOfficer = employeeRepository.findEmployeeByEmail(email);
        Loan loan = new Loan(
                customer.get().getProduct(),
                customer.get(),
                facility,
                loanDTO.getAmount(),
                loanDTO.getRepayments(),
                Status.ONE
        );

        if (customer.get().getProduct().isGranted())
            customer.get().setGrantorNationalId(loanDTO.getGrantorNationalId());
        customerRepository.findCustomerByNationalId(loanDTO.getGrantorNationalId())
                .get().addLoanToGrantedCustomer(loan);
        loanOfficer.get().addLoanToLoanOfficer(loan);
        employeeRepository.save(loanOfficer.get());
        return ResponseEntity.status(OK).body("You can complete the steps to the next window PLEASE!");
    }

    private ResponseEntity<String> validateRequestedLoan(Optional<Customer> customer, LoanDTO loanDTO, Product product) {
        if (customer.get().getProduct() == null)
            return ResponseEntity.status(BAD_REQUEST).body("You must pay the commission");
        if (!customer.get().getProduct().getProductType().name().equals(product.getProductType().name()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to choose the same product you requested before");

        if (!customer.get().isCommissionPaid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have to pay the commission");

        if (!customer.get().getCommissionPaidDate().plusMonths(3L).isAfter(LocalDate.now()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Expired");

        if (loanDTO.getRepayments() < product.getMinDuration())
            return ResponseEntity.status(BAD_REQUEST).body("Number of repayment is less than the minimum");

        if (loanDTO.getRepayments() > product.getMaxDuration())
            return ResponseEntity.status(BAD_REQUEST).body("Number of repayment is more than the maximum");

        if (loanDTO.getAmount() < product.getMinimum())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested amount is less than the min");

        if (loanDTO.getAmount() > product.getMaximum())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested amount is more than than the max");
        if (product.isGranted() == true) {
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

    public ResponseEntity<?> confirmLoanRevision(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (!loanOptional.isPresent())
            return ResponseEntity.status(BAD_REQUEST).body("Cannot find Loan");

        loanOptional.get().setStatus(Status.TWO);
        loanRepository.save(loanOptional.get());
        return ResponseEntity.status(OK).body("Loan Revised Proceed to manager");
    }

    public ResponseEntity<?> confirmLoan(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (!loanOptional.isPresent())
            return ResponseEntity.status(BAD_REQUEST).body("Cannot find Loan");

        loanOptional.get().setStatus(Status.THREE);
        loanRepository.save(loanOptional.get());
        return ResponseEntity.status(OK).body("Loan Confirmed");
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
                if (l.getStatus().equals(Status.TWO))
                    loans.add(l);
            }
        }
        return ResponseEntity.status(OK).body(loans);
    }

    public ResponseEntity<?> cashLoan(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if(!loanOptional.isPresent())
            return ResponseEntity.status(BAD_REQUEST).body("Enter a correct loan ID");
        Loan loan= loanOptional.get();
        if(!loan.getStatus().equals(Status.THREE))
            return ResponseEntity.status(BAD_REQUEST).body("The loan has not been confirmed yet!");

        RepaymentSchedule repaymentSchedule = new RepaymentSchedule(
                loan.getAmount(),
                loan.getAmount()/loan.getNumberOfRepayments(),
                LocalDate.now(),
                LocalDate.now().plusMonths(loan.getNumberOfRepayments()),
                0,
                loan.getNumberOfRepayments()
        );
        loan.addRepaymentSchedule(repaymentSchedule);
        loanRepository.save(loan);
        RepaymentScheduleDTO repaymentScheduleDTO = mapper.map(repaymentSchedule,RepaymentScheduleDTO.class);
        return ResponseEntity.status(OK).body(repaymentScheduleDTO);
    }
}

//facility id instead of facility Name