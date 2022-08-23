package com.example.tanmeyah.customer.domain;

import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.facility.Facility;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Customer")
@Table(name = "customer")
@Getter
@Setter
public class Customer {
    public Customer(String firstName, String lastName,
                    String phoneNumber,
                    String nationalId,
                    boolean isCommissionPaid,
                    LocalDate commissionPaidDate,
                    double commissionAmount
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.nationalId = nationalId;
        this.isCommissionPaid = isCommissionPaid;
        this.commissionPaidDate = commissionPaidDate;
        this.commissionAmount=commissionAmount;

    }

    @Id
    @SequenceGenerator(
        name = "customer_sequence",
        sequenceName = "customer_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "customer_sequence"
    )
    @Column(
        name = "id",
        updatable = false
    )
    private Long id;

    @Column(
        name = "first_name",
        nullable = false

    )
    private String firstName;

    @Column(
        name = "last_name",
        nullable = false


    )
    private String lastName;

    @Column(
        name = "phone_number",
        nullable = false
    )
    private String phoneNumber;

    @Column(
        name = "national_id",
        nullable = false,
        unique = true
    )
    @Pattern(
        regexp = "^([1-9]{1})([0-9]{2})([0-9]{2})([0-9]{2})([0-9]{2})[0-9]{3}([0-9]{1})[0-9]{1}$",
        message = "Invalid National ID number"
    )
    private String nationalId;


    @Column(
        name = "start_date"
    )
    private LocalDate startDate;

    @Column(
        name = "end_date"
    )
    private LocalDate endDate;


    @Column(
        name = "isCommissionPaid")
    private boolean isCommissionPaid;

    @Column(
        name = "commissionPaidDate"
    )
    private LocalDate commissionPaidDate;

    @ManyToOne()
    @JoinColumn(
        name = "branch_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(
            name = "branch_customer_fk_id"
        )

    )
    @JsonIgnore
    private Branch branch;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
        name = "product_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(
            name = "customer_product_id_fk"
        )
    )
    private Product product;


    @JsonIgnore
    @OneToOne(
        mappedBy = "customer"
    )

    private Facility facility;
    @JsonIgnore
    @OneToMany(
        mappedBy = "grantedCustomer"

    )
    private List<Loan> loansOfGrantedCustomer = new LinkedList<>();
    private double requestedAmount;
    @Column(name = "commission_amount")
    private double commissionAmount;

    @Transactional
//    public void addLoanToGrantedCustomer(Loan loan) {
//        if (!loansOfGrantedCustomer.contains(loan)) {
//            loansOfGrantedCustomer.add(loan);
//            System.out.println(this);
//            loan.setGrantedCustomer(this);
//        }
//    }
    public void addLoanToGrantedCustomer(Loan loan) {
        loansOfGrantedCustomer.add(loan);
        loan.setGrantedCustomer(this);
    }


    public void removeLoanFromGrantedCustomer(Loan loan) {
        if (loansOfGrantedCustomer.contains(loan)) {
            loansOfGrantedCustomer.remove(loan);
            loan.setCustomer(null);
        }
    }

    public void addFacility(Facility facility) {
        this.setFacility(facility);
        facility.setCustomer(this);

    }

    public Customer() {

    }
}
