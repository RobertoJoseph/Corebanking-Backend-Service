package com.example.tanmeyah.branch.domain;

import com.example.tanmeyah.customer.domain.Customer;
import com.example.tanmeyah.employee.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity(
    name = "Branch"
)
@Table(
    name = "branch"
)
@NoArgsConstructor
@Getter
@Setter
//@ToString

public class Branch {

    public Branch(String branchName, String branchAddress) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;


    }

    @JsonIgnore
    @Id
    @SequenceGenerator(
        name = "branch_sequence",
        sequenceName = "branch_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "branch_sequence"
    )
    @Column(
        name = "id",
        updatable = false
    )
    private Long id;

    @Column(
        name = "branch_name",
        nullable = false
    )
    private String branchName;

    @Column(
        name = "branch_address",
        nullable = false
    )
    private String branchAddress;

    @OneToMany(
        mappedBy = "branch",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )

    private List<Employee> employeeList = new ArrayList<>();

    @OneToMany(
        mappedBy = "branch",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    List<Customer> customers = new LinkedList<>();

    public void addCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            customers.add(customer);
            customer.setBranch(this);
        }
    }

    public void removeCustomer(Customer customer) {
        if (customers.contains(customer)) {
            customers.remove(customer);
            customer.setBranch(null);
        }
    }


    public void addEmployee(Employee employee) {
        if (!employeeList.contains(employee)) {
            employeeList.add(employee);
            employee.setBranch(this);
        }
    }

    public void removeEmployee(Employee employee) {
        if (employeeList.contains(employee)) {
            employeeList.remove(employee);
            employee.setBranch(null);
        }
    }

    @ManyToOne
    @JoinColumn(
        name = "branch_parent_id",
        referencedColumnName = "id"
    )
    private Branch parent;

    @OneToMany(
        mappedBy = "parent",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )

    @JsonIgnore
    private List<Branch> branches = new ArrayList<>();


    public List<Branch> getBranches() {
        return branches;
    }

    public void addBranch(Branch branch) {
        if (!branches.contains(branch)) {
            System.out.println(this);
            branches.add(branch);
            branch.setParent(this);
        }
    }
}
