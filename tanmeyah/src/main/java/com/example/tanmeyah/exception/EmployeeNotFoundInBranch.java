package com.example.tanmeyah.exception;

public class EmployeeNotFoundInBranch extends  RuntimeException {

    public EmployeeNotFoundInBranch(String message) {
        super(message);
    }
}
