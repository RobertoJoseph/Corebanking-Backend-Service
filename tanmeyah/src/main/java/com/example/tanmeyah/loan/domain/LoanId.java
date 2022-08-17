package com.example.tanmeyah.loan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class LoanId implements Serializable {
    @Column(
        name = "product_id"
    )
    private Long productId;

    @Column(
        name = "customer_id"
    )
    private Long customerId;

    public LoanId() {
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
