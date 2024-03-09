package com.example.monywell.controllers;

import com.example.monywell.model.CustomerAccounts;
import com.example.monywell.modelValidator.AccountNumberValidator;
import com.example.monywell.modelValidator.Credit;
import com.example.monywell.modelValidator.Transfer;
import com.example.monywell.modelValidator.Withdrawal;
import com.example.monywell.services.CustomerAccountService;
import com.example.monywell.services.TransactonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/")
public class CustomerAccountController {
    private final CustomerAccountService customerAccountService;

    private final TransactonService transactonService;

    @Autowired
    private CustomerAccountController(CustomerAccountService customerAccountService, TransactonService transactonService) {

        this.customerAccountService = customerAccountService;
        this.transactonService = transactonService;
    }

    @PostMapping("customer-account/")
    public ResponseEntity<CustomerAccounts> getCustomerAccount(@RequestBody @Valid AccountNumberValidator e) {

        return customerAccountService.getCustomerAccountByAccountNumber(e);
    }

    @PostMapping("withdraw/")
    public ResponseEntity<Map<String, String>> withdrawalFromAccount(@RequestBody @Valid Withdrawal withdrawal) {

        return transactonService.withdrawFromAccount(withdrawal);
    }

    @PostMapping("credit/")
    public ResponseEntity<Map<String, String>> creditAccount(@RequestBody @Valid Credit credit) {

        return transactonService.creditAccount(credit);
    }

    @PostMapping("transfer/")
    public ResponseEntity<Map<String, String>> transfer(@RequestBody @Valid Transfer transfer) {

        return transactonService.transferFromAccount(transfer);
    }
    @GetMapping("interest/{accountNumber}")
    public ResponseEntity<Map<String, String>> interest(@PathVariable String accountNumber){
        System.out.println(accountNumber);
        return customerAccountService.pullCustomerInterest(accountNumber);
    }
}
