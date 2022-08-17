package com.example.tanmeyah.branch.bodyRequests;

import com.example.tanmeyah.branch.domain.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class BranchCreation {
    private final String branchName;
    private final String branchAddress;
    private final Long parentBranchId;
}
