package com.example.tanmeyah.employee.domain;

import com.example.tanmeyah.branch.domain.Branch;
import com.example.tanmeyah.loan.domain.Loan;
import com.example.tanmeyah.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity(
    name = "Employee"
)
@Table(
    name = "employee"
)
@NoArgsConstructor
@Getter
@Setter
//@ToString
public class Employee implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Id
    @SequenceGenerator(
        name = "employee_sequence",
        sequenceName = "employee_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "employee_sequence"
    )

    @Column(
        name = "id",
        updatable = false
    )
    private Long id;

    @NotBlank(message = "First name must not be empty")
    @Column(
        name = "firstName",
        nullable = false
    )
    private String firstName;


    @NotBlank(message = "Last name must not be empty")
    @Column(
        name = "lastName",
        nullable = false
    )
    private String lastName;

    @Column(
        nullable = false,
        unique = true
    )
    @Email(
        message = "Check email constraints"
    )

    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne
    @JoinColumn(
        name = "branch_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(
            name = "branch_id_fk"
        )
    )
    @JsonIgnore
    private Branch branch;

    public Employee(String firstName, String lastName,
                    String email, String password,
                    Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @JsonIgnore
    @OneToMany(
        mappedBy = "loanOfficer",
        cascade = CascadeType.ALL)
    private List<Loan> loansOfLoanOfficer = new LinkedList<>();


    public void addLoanToLoanOfficer(Loan loan) {
        if (!loansOfLoanOfficer.contains(loan)) {
            loansOfLoanOfficer.add(loan);
//            loan.setBranchId(this.getBranch().getId());
            loan.setLoanOfficer(this);
        }
    }

    public void removeLoanFromLoanOfficer(Loan loan) {
        if (loansOfLoanOfficer.contains(loan)) {
            loansOfLoanOfficer.remove(loan);
            loan.setLoanOfficer(null);
        }
    }


}
