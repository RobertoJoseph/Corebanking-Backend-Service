package com.example.tanmeyah.product;

import com.example.tanmeyah.customer.domain.Customer;
import lombok.Getter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Product")
@Table(name = "product")
@Getter
public class Product {

    @Id
    @SequenceGenerator(
        name = "product_sequence",
        sequenceName = "product_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "product_sequence"
    )
    @Column(
        name = "id",
        updatable = false
    )
    private Long id;


    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;


    @OneToMany(
        mappedBy = "product",
        orphanRemoval = true,
        cascade = CascadeType.ALL

    )
    private List<Customer> customerList = new LinkedList<>();

    public Product(ProductType productType) {

        this.productType = productType;
    }

    public Product() {
    }

    public void addCustomer(Customer customer) {

        customerList.add(customer);
        customer.setProduct(this);

    }

    public void removeCustomer(Customer customer) {
        if (customerList.contains(customer)) {
            customerList.remove(customer);
            customer.setProduct(null);
        }
    }


}
