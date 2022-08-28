package com.example.tanmeyah.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfirmLoanDTO {
    private Long productId;
    private Long customerId;
}
