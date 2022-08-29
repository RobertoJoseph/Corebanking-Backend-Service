package com.example.tanmeyah.loan;

import com.example.tanmeyah.product.constant.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanDTO {
    private String customerNationalId;
    private String facilityName;
    private Long productId;
    private double amount;
    private int repayments;
    private String grantorNationalId;
}
