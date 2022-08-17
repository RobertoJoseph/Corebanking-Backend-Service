package com.example.tanmeyah.employee.controller;

import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "tanmeyah/user/update")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PutMapping(path = "{employeeId}")
    public ResponseEntity<?>
    updateEmployeeInfo(@PathVariable("employeeId") Long employeeId,
                       @RequestBody Employee employee) {
        return employeeService.updateEmployeeInformation(employeeId, employee);
    }
}
