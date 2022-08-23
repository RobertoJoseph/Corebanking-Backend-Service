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
import com.example.tanmeyah.product.ProductType;
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

import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
@EnableAsync
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final FacilityRepository facilityRepository;
    private final ProductRepository productRepository;
    private ModelMapper mapper;


    //    public ResponseEntity<CustomerDTO> getCustomerById(Long id) {
//        Optional<Customer> customer = customerRepository.findById(id);
//        CustomerDTO customerDTO = mapper.map(customer.get(), CustomerDTO.class);
//        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
//    }
    public ResponseEntity<?> getCustomerByNationalId(String nationalId) {
        Optional<Customer> customer = customerRepository.findCustomerByNationalId(nationalId);
        if (customer.get() == null)
            return ResponseEntity.status(OK).body("Cannot find this customer");
        CustomerDTO customerDTO = mapper.map(customer.get(), CustomerDTO.class);
        return ResponseEntity.status(OK).body(customerDTO);
    }


    @Transactional
    public ResponseEntity<?> addCustomer(CustomerDTO customerDTO) throws JSONException {
        Optional<Branch> branch = branchRepository.findBranchById(customerDTO.getBranchId());
        if(branch.get()==null)
            return ResponseEntity.status(OK).body("Incorrect Branch Id");
//        System.out.println(customerDTO.getIsCommissionPaid());
        Customer c = new Customer(
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNationalId(),
                false
        );
        branch.get().addCustomer(c);
        branchRepository.save(branch.get());
        //TODO JSON OBJECT CREATION
        return ResponseEntity.status(OK).body("Added");

    }

    public ResponseEntity<?> addCommissionToCustomer(CustomerDTO customerDTO) {
        if(customerDTO.getCommissionAmount()==customerDTO.getProductType().getCommission()){
            Product product = new Product(customerDTO.getProductType());
            Optional<Customer> customerOptional = customerRepository
                    .findCustomerByNationalId(customerDTO.getNationalId());
           if(customerOptional.get()==null)
               return ResponseEntity.status(OK).body("Enter a correct National ID");
            customerOptional.ifPresent(customer -> {
                customer.setCommissionPaid(true);
                customer.setCommissionAmount(customerDTO.getCommissionAmount());
                customer.setCommissionPaidDate(LocalDate.now());
                product.addCustomer(customer);
                productRepository.save(product);

            });
            CustomerDTO ret= mapper.map(customerOptional.get(),CustomerDTO.class);
            return ResponseEntity.status(OK).body(ret);
        }else{
            return ResponseEntity.status(OK)
                    .body("Commission amount is not compatible with this product type");
        }
    }
}
