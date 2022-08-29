package com.example.tanmeyah;

import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.branch.repository.BranchRepository;
import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.repository.EmployeeRepository;
import com.example.tanmeyah.facility.FacilityRepository;
import com.example.tanmeyah.product.domain.Product;
import com.example.tanmeyah.product.repository.ProductRepository;
import com.example.tanmeyah.product.constant.ProductType;
import com.example.tanmeyah.security.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class TanmeyahApplication {
    private final FacilityRepository facilityRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TanmeyahApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            System.out.println("Iam here down to babe");
            Branch branch = new Branch("Tanmeyah", "Maaadi");
            branchRepository.save(branch);

            Employee admin = new Employee(
                    "Ahmed",
                    "Khedr",
                    "ahmed@tanmeyah.com",
                    passwordEncoder.encode("1234"),
                    Role.ADMIN
            );
            employeeRepository.save(admin);

            Product classA = new Product(
                    ProductType.CLASS_A,
                    1000,
                    10000,
                    false,
                    12,
                    18,
                    100
            );
            Product classB = new Product(
                    ProductType.CLASS_B,
                    10001,
                    50000,
                    false,
                    12,
                    24,
                    125
            );
            Product classC = new Product(
                    ProductType.CLASS_C,
                    50001,
                    100000,
                    true,
                    12,
                    32,
                    150
            );
            productRepository.saveAll(List.of(classA, classB, classC));
        };
    }

}
