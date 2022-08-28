package com.example.tanmeyah;

import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.branch.repository.BranchRepository;
import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.repository.EmployeeRepository;
import com.example.tanmeyah.facility.Facility;
import com.example.tanmeyah.facility.FacilityRepository;
import com.example.tanmeyah.loan.LoanRepository;
import com.example.tanmeyah.loan.constant.Status;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.product.Product;
import com.example.tanmeyah.product.ProductType;
import com.example.tanmeyah.security.Role;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class TanmeyahApplication {
    private final FacilityRepository facilityRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    public static void main(String[] args) {
        SpringApplication.run(TanmeyahApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner(LoanRepository loanRepository) {
        return args -> {
            System.out.println("Iam here down to babe");
            Branch branch = new Branch("Tanmeyah", "Maaadi");
            branchRepository.save(branch);
        };
    }

}
