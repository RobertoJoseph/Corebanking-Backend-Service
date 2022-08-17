package com.example.tanmeyah.registration;

import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmployeeService employeeService;

    public ResponseEntity<?>
    registerEmployee(RegistrationRequest registrationRequest) {
        return employeeService.signUpUser(
            new Employee(
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                registrationRequest.getRole()
            )
        );
    }
}
