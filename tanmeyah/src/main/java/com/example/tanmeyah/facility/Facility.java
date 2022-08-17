package com.example.tanmeyah.facility;

import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.loan.domain.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(
    name = "Facility"
)
@Table(
    name = "facility"
)
@Getter
@Setter
public class Facility {
    public Facility() {
    }

    @Id
    @SequenceGenerator(
        name = "facility_sequence",
        sequenceName = "facility_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "facility_sequence"
    )
    @Column(
        name = "id",
        updatable = false
    )
    private Long id;

    public Facility(String facilityName, Customer customer) {
        this.facilityName = facilityName;
        this.customer = customer;
    }

    @Column(
        name = "facility_name"
    )
    private String facilityName;

    @JsonIgnore
    @OneToOne(
        cascade = {CascadeType.PERSIST}
    )
    @JoinColumn(
        name = "customer_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(
            name = "facility_customer_id_fk"
        )
    )
    private Customer customer;

    @JsonIgnore
    @OneToOne(
        mappedBy = "facility"
    )
    private Loan loan;
}
