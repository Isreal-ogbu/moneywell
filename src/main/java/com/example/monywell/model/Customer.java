package com.example.monywell.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"customerId", "mobile", "email"}))
public final class Customer implements GetDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "phone number cannot be null")
    @NotBlank(message = "phone number cannot be blank")
    @NotEmpty(message = "phone number cannot be empty")
    @Pattern(regexp = "^\\+234\\d{10}$")
    private String mobile;

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "must enter a valid email")
    private String email;

    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    @NotEmpty(message = "username cannot be empty")
    private String userName;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    @NotEmpty(message = "password cannot be empty")
    @Length(min = 8, max = 50, message = "min length of password is 8")
    private String password;

    @NotNull(message = "address cannot be null")
    @NotBlank(message = "address cannot be blank")
    @NotEmpty(message = "address cannot be empty")
    private String address;

    private int noOfAccounts = 0;
    private boolean active = true;


    public Customer(String name, String mobile, String email, String userName, String password, String address) {
        this.name=name;
        this.mobile=mobile;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.address = address;
        noOfAccounts++;
    }

    @Override
    public Map<String, String> getDetail() {

        Map<String, String> details = new HashMap<>();
        details.put("Name", this.name);
        details.put("Email", this.email);
        details.put("Phone number", this.mobile);
        details.put("Number of accounts", String.valueOf(noOfAccounts));
        details.put("Active", String.valueOf(active));

        return details;
    }
}
