package com.example.tanmeyah.product.domain;

import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.product.constant.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Product")
@Table(name = "product")
@Getter
@Setter
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
    @Column(name = "minimum")
    private double minimum;

    @Column(name = "maximum")
    private double maximum;

    @Column(name = "is_granted")
    private boolean isGranted;

    @Column(name = "min_duration")
    private int minDuration;

    @Column(name = "max_duration")
    private int maxDuration;

    @Column(name = "commission")
    private double commission;

    public Product(ProductType productType,
                   double minimum, double maximum, boolean isGranted,
                   int minDuration, int maxDuration, double commission) {
        this.productType = productType;
        this.minimum = minimum;
        this.maximum = maximum;
        this.isGranted = isGranted;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.commission = commission;
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
