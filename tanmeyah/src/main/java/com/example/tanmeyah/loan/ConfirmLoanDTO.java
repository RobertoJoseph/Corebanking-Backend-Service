package com.example.tanmeyah.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfirmLoanDTO {
    private String nationalId;
    private String grantorNationalId;
}
