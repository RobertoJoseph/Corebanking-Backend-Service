package com.example.tanmeyah.customer.controller;

import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.customer.requests.CustomerDTO;
import com.example.tanmeyah.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tanmeyah/customer")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customer) throws JSONException {

        return customerService.addCustomer(customer);
    }

//    @GetMapping
//    public ResponseEntity<CustomerDTO> getCustomer(@RequestBody Long id) throws SQLException {
//        return customerService.getCustomerById(id);
//    }
    @GetMapping
    public ResponseEntity<?> getCustomer(@RequestBody String nationalId){
        return customerService.getCustomerByNationalId(nationalId);
    }

}
