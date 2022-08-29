package com.example.tanmeyah.customer.requests;

import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.product.domain.Product;
import com.example.tanmeyah.product.constant.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String nationalId;
    private String facilityName;
    private Long branchId;
    private Long productId;
    private Boolean isCommissionPaid;
    private LocalDate commissionPaidDate;
    private List<Loan> loansOfGrantedCustomer;
    private ProductType productType;
    private double commissionAmount;


}
