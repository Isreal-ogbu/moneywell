package com.example.monywell.model;

import com.example.monywell.model.model_enum.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.lang.System.in;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerAccounts implements GetDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;

    @ManyToOne(targetEntity = Customer.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customerId", nullable = false, unique = false)
    private Customer customerId;

    private Long accountNumber;

    private Double balance = 0.00;

    private Double interest = 0.00;

    private Long pin;

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    private LocalDate created = LocalDate.now();

    private LocalDate updated = LocalDate.now();

    private boolean active = true;

    public CustomerAccounts(Customer customerId, String substring, String pin, AccountType accountType) {
//        Opening an account without instant funding

        this.customerId = customerId;
        this.accountNumber = Long.parseLong(substring);
        this.pin = Long.parseLong(pin);
        this.accountType = Objects.requireNonNullElse(accountType, AccountType.FLEX);
    }

    public CustomerAccounts(Customer customer1, String substring, String pin, AccountType accountType, double initialBalance) {
//        Opening an account instantly with credit
        this.customerId = customer1;
        this.accountNumber = Long.parseLong(substring);
        this.pin = Long.parseLong(pin);
        this.accountType = Objects.requireNonNullElse(accountType, AccountType.FLEX);
        this.balance = initialBalance;
    }

    public void fetchBalanceWithInterest(){

        long daysDifference = ChronoUnit.DAYS.between(LocalDate.now(), this.updated);

        if (daysDifference < 365){ }

        else{

        int yearCount = (int) (daysDifference % 365);

        switch (accountType){
            case FLEX -> {
                setInterest(calculateInterest(0.025,yearCount, this.balance));
            }
            case SUPA -> {
                setInterest(calculateInterest(0.1,yearCount, this.balance));
            }
            case VIVA -> {
                setInterest(calculateInterest(0.06,yearCount, this.balance));
            }
            case PIGGY -> {
                setInterest(calculateInterest(0.092,yearCount, this.balance));
            }
            case DELUXE -> {
                setInterest(calculateInterest(0.035,yearCount, this.balance));
            }
        }
    }
    }

    public static Double calculateInterest(Double rate, int yearcount, Double balance){
        return yearcount * rate * balance;
    }

    @Override
    public Map<String, String> getDetail() {
//        Fetch customer account details

        Map<String, String> details = new HashMap<>();

        details.put("accountType", String.valueOf(this.getAccountType()));
        details.put("accountNumber", String.valueOf(this.getAccountNumber()));
        details.put("accountBalance", String.valueOf(this.getBalance()));
        details.put("Interest", String.valueOf(this.getInterest()));

        return details;
    }
}
