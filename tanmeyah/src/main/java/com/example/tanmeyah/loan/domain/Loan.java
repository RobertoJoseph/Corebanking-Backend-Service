package com.example.tanmeyah.loan.domain;

import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.employee.domain.Employee;
import com.example.tanmeyah.facility.Facility;
import com.example.tanmeyah.loan.constant.Status;
import com.example.tanmeyah.product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(
    name = "Loan"
)
@Table(
    name = "loan"
)
@Getter
@Setter
public class Loan {
    public Loan() {

    }

    public Loan(Product product, Customer customer,
                Facility facility, double amount,
                int numberOfRepayments, Status status) {
        this.product = product;
        this.customer = customer;
        this.facility = facility;
        this.amount = amount;
        this.numberOfRepayments = numberOfRepayments;
        this.status = status;
    }

    @Id
    @SequenceGenerator(
            name = "loan_sequence",
            sequenceName = "loan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loan_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @OneToOne
    @MapsId(
        "productId"
    )
    @JoinColumn(
        name = "product_id",
        foreignKey = @ForeignKey(
            name = "loan_product_fk"
        )
    )
    private Product product;

    @OneToOne(
        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @MapsId(
        "customerId"
    )
    @JoinColumn(
        name = "customer_id",
        foreignKey = @ForeignKey(
            name = "loan_customer_fk"
        )
    )
    private Customer customer;

    @OneToOne(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(
        name = "facility_id"
    )
    private Facility facility;

    @ManyToOne
    @JoinColumn(
        name = "granted_customer_id",
        referencedColumnName = "id"
    )
    private Customer grantedCustomer;

    @ManyToOne
    @JoinColumn(
        name = "loan_officer_id"
    )
    private Employee loanOfficer;

    @Column(
        name = "amount"
    )
    private double amount;

    @Column(
        name = "number_of_repayments"
    )
    private int numberOfRepayments;


    @Enumerated(EnumType.STRING)
    private Status status;

    public void addProduct(Product product) {
        setProduct(product);
    }


}
