package com.example.tanmeyah.branch.controller;

import com.example.tanmeyah.branch.bodyRequests.BranchCreation;
import com.example.tanmeyah.branch.bodyRequests.BranchUpdate;
import com.example.tanmeyah.branch.service.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("tanmeyah/branch")
@AllArgsConstructor
public class BranchController {

    public final BranchService branchService;

    @GetMapping(path = "{branchId}")
    public ResponseEntity<?> getBranch(@PathVariable("branchId") Long branchId) {
        return branchService.getBranch(branchId);
    }

    @PostMapping
    public ResponseEntity<?> addBranch(@Valid @RequestBody BranchCreation branchCreation) {
        return branchService.addBranch(branchCreation);
    }

    @PutMapping()
    public ResponseEntity<?>
    updateBranch( @Valid @RequestBody BranchUpdate branchUpdate) {
        return branchService.updateBranch(branchUpdate.getBranchName(),
            branchUpdate.getBranchAddress(),
            branchUpdate.getBranchId());
    }

    @DeleteMapping(path = "{branchId}")
    public ResponseEntity<?>
    deleteBranch(@PathVariable("branchId") Long branchId) {
        return branchService.deleteBranch(branchId);
    }

    @PutMapping(path = "{branchId}")
    public ResponseEntity<?> addEmployeesToBranch(
        @PathVariable("branchId") Long branchId,
        @RequestBody Long[] employeesId
    ) {
        return branchService.addEmployeesToBranch(branchId, employeesId);
    }

    @DeleteMapping(path = "delete/{branchId}")
    public ResponseEntity<?> removeEmployeesFromBranch(@PathVariable("branchId") Long branchId,
                                                       @RequestBody Long[] employeesId) {
        return branchService.removeEmployeesFromBranch(branchId, employeesId);

    }
}
