package com.example.tanmeyah.customer.controller;

import com.example.tanmeyah.customer.repository.CustomerRepository;
import com.example.tanmeyah.customer.requests.CustomerDTO;
import com.example.tanmeyah.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("customer")
@AllArgsConstructor

public class CustomerController {
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @PostMapping()
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customer) throws JSONException {

        return customerService.addCustomer(customer);
    }

    @GetMapping
    public ResponseEntity<CustomerDTO> getCustomer(@RequestBody Long id) throws SQLException {
        return customerService.getCustomerById(id);
    }
}
