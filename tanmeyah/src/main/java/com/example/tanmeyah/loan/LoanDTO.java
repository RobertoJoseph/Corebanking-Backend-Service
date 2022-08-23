package com.example.tanmeyah.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanDTO {
    private String customerNationalId;
    private long facilityId;
    private long productId;
    private double amount;
    private int repayments;
    private String grantedNationalId;
}
