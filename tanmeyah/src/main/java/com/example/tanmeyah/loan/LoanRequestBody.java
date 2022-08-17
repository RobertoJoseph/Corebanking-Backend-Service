package com.example.tanmeyah.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanRequestBody {
    private long customerId;
    private long facilityId;
    private long productId;
    private double amount;
    private int repayments;
}
