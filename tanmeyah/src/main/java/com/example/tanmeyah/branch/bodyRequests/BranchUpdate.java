package com.example.tanmeyah.branch.bodyRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class BranchUpdate {
    @NotBlank(message = "Branch name must not be empty")
    private final String branchName;
    @NotBlank(message = "Branch address must not be empty")
    private final String branchAddress;

    @NotNull(message = "Branch Id must not be empty")
    private final Long branchId;
}
