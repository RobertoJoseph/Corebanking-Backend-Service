package com.example.tanmeyah.customer.service;

import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.branch.repository.BranchRepository;
import com.example.tanmeyah.customer.requests.CustomerDTO;
import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.facility.Facility;
import com.example.tanmeyah.facility.FacilityRepository;
import com.example.tanmeyah.product.Product;
import com.example.tanmeyah.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableAsync
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final FacilityRepository facilityRepository;
    private final ProductRepository productRepository;
    private ModelMapper mapper;


    public ResponseEntity<CustomerDTO> getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        CustomerDTO customerDTO = mapper.map(customer.get(), CustomerDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }


    @Transactional
    public ResponseEntity<?> addCustomer(CustomerDTO customerDTO) throws JSONException {
        Optional<Branch> branch = branchRepository.findBranchById(customerDTO.getBranchId());
        System.out.println(customerDTO.getIsCommissionPaid());
        Customer c = new Customer(
            customerDTO.getFirstName(),
            customerDTO.getLastName(),
            customerDTO.getEmail(),
            customerDTO.getPhoneNumber(),
            customerDTO.getNationalId(),
            customerDTO.getIsCommissionPaid(),
            LocalDate.now(),
            customerDTO.getRequestedAmount()
        );
        Facility facility = new Facility(customerDTO.getFacilityName(), c);
        Product p = new Product(customerDTO.getProductType());
        facilityRepository.save(facility);
        branch.get().addCustomer(c);
        p.addCustomer(c);
        productRepository.save(p);
        branchRepository.save(branch.get());
        //TODO JSON OBJECT CREATION
        return ResponseEntity.status(HttpStatus.OK).body("Added ");

    }
}
