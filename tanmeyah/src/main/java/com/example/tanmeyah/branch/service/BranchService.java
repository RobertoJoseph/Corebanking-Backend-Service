package com.example.tanmeyah.branch.service;

import com.example.tanmeyah.branch.bodyRequests.BranchCreation;
import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.branch.repository.BranchRepository;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.employee.repository.EmployeeRepository;
import com.example.tanmeyah.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BranchService {
    public final BranchRepository branchRepository;

    public final EmployeeRepository employeeRepository;

    private Optional<Branch> findBranchById(Long id) {
        return branchRepository.findBranchById(id);
    }

    public ResponseEntity<?> addBranch(BranchCreation branchCreation) {
        Optional<Branch> parentDatabase = Optional.ofNullable(findBranchById(branchCreation.getParentBranchId()).
            orElseThrow(() -> new NotFoundException(String.format("Branch Parent id %s not found", branchCreation.getParentBranchId()))));
        parentDatabase.get().addBranch(new Branch(branchCreation.getBranchName(), branchCreation.getBranchAddress()));
        branchRepository.save(parentDatabase.get());
        return ResponseEntity.status(HttpStatus.OK)
            .body(branchCreation);

    }

    public ResponseEntity<?> getBranch(Long id) {
        Optional<Branch> branch = Optional.ofNullable(findBranchById(id).
            orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", id))));
        return ResponseEntity.status(HttpStatus.OK).body(branch);

    }

    public ResponseEntity<?> updateBranch(
        String branchName, String branchAddress, Long id) {
        Optional<Branch> branch = Optional.ofNullable(findBranchById(id).
            orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", id))));

        branchRepository.updateBranch(branchName, branchAddress, id);
        return ResponseEntity.status(HttpStatus.OK).body(branch);
    }

    public ResponseEntity<?> deleteBranch(Long branchId) {
        Optional<Branch> branch = Optional.ofNullable(findBranchById(branchId).
            orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", branchId))));

        for (Branch br : branch.get().getBranches()) {
            br.setParent(branch.get().getParent());
        }
        branchRepository.deleteById(branchId);
        return ResponseEntity.status(HttpStatus.OK).body
            (branch);
    }

    public ResponseEntity<?> addEmployeesToBranch(Long branchId,
                                                  Long[] employeesId) {
        Optional<Branch> branch = Optional.ofNullable(branchRepository.findBranchById(branchId).
            orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", branchId))));
        for (Long id : employeesId) {
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) branch.get().addEmployee(employee.get());
        }
        branchRepository.save(branch.get());
        return ResponseEntity.status(HttpStatus.OK).body
            (branch.get());
    }

    public ResponseEntity<?>
    removeEmployeesFromBranch(Long branchId, Long[] employeesId) {
        Optional<Branch> branch = Optional.ofNullable(findBranchById(branchId).
            orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", branchId))));
        for (Long id : employeesId) {
            Optional<Employee> employee = employeeRepository.findById(id);
//            branch.get().getEmployeeList().remove(employee.get());
            branch.get().removeEmployee(employee.get());
        }

        branchRepository.save(branch.get());

        return ResponseEntity.status(HttpStatus.OK).body(branch);
    }

//    public ResponseEntity<?> addCustomersToBranch(Long branchId, Customer[] customers) {
//        Optional<Branch> branch = Optional.ofNullable(findBranchById(branchId).
//            orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", branchId))));
//        for (Customer customer : customers) {
//            branch.get().addCustomer(customer);
//        }
//
//    }
}
