package com.example.monywell.services;

import com.example.monywell.exception.CustomerException;
import com.example.monywell.model.CustomerAccounts;
import com.example.monywell.modelValidator.AccountNumberValidator;
import com.example.monywell.repository.CustomerAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerAccountService {
    private final CustomerAccountsRepository customerAccountsRepository;
    @Autowired
    public CustomerAccountService(CustomerAccountsRepository customerAccountsRepository){
        this.customerAccountsRepository = customerAccountsRepository;
    }

    public ResponseEntity<CustomerAccounts> getCustomerAccountByAccountNumber(AccountNumberValidator accountNumberValidator){
        CustomerAccounts customerAccounts = customerAccountsRepository.findByAccountNumber(Long.parseLong(
                accountNumberValidator.getCustomerAccountNumber())).orElseThrow(()-> new CustomerException("Account does not exist. Check account number"));
        customerAccounts.setPin(null);
        customerAccounts.getCustomerId().setPassword("*******");
        return ResponseEntity.status(HttpStatus.OK).body(customerAccounts);
    }

    public ResponseEntity<Map<String, String>> pullCustomerInterest(String accountNumber){
        CustomerAccounts customerAccounts = customerAccountsRepository.findByAccountNumber(Long.parseLong(
                accountNumber)).orElseThrow(()-> new CustomerException("Account does not exist. Check account number"));
        customerAccounts.fetchBalanceWithInterest();
        return ResponseEntity.status(HttpStatus.OK).body(customerAccounts.getDetail());
    }
}
