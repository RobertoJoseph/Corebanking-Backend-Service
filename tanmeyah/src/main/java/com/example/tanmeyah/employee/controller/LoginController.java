package com.example.tanmeyah.employee.controller;

import com.example.tanmeyah.employee.LoginDTO;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
   private final EmployeeService employeeService;
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws JSONException {
      return employeeService.login(loginDTO);
    }
}
