package com.example.tanmeyah.employee.service;

import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService implements UserDetailsService {


    public final EmployeeRepository employeeRepository;
    private final static String USER_NOT_FOUND = "user with email %s not found";

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findEmployeeByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public ResponseEntity<?> signUpUser(Employee employee) {
        boolean isUserExists = employeeRepository.findEmployeeByEmail(employee.getEmail()).isPresent();
        if (isUserExists) {

            return new ResponseEntity<>(String.format("The user with email %s already found",
                employee.getEmail()),
                HttpStatus.BAD_REQUEST);
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        employeeRepository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

    public ResponseEntity<?>
    updateEmployeeInformation(Long employeeId, Employee employee) {
        Optional<Employee> e = employeeRepository.findById(employeeId);
        Employee currentEmployee = e.get();
        if (employee.getFirstName() != null)
            currentEmployee.setFirstName(employee.getFirstName());
        if (employee.getLastName() != null)
            currentEmployee.setLastName(employee.getLastName());
        if (employee.getEmail() != null)
            currentEmployee.setEmail(employee.getEmail());
        if (employee.getPassword() != null)
            currentEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(currentEmployee);
        return ResponseEntity.status(HttpStatus.OK).body(currentEmployee);

    }


}
