package com.example.tanmeyah.security;

public enum ApplicationUserPermission {
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    BRANCH_READ("branch:read"),
    BRANCH_WRITE("branch:write");

    public String getPermission() {
        return permission;
    }

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
}
