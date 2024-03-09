package com.example.monywell.services;

import com.example.monywell.exception.CustomerException;
import com.example.monywell.model.Customer;
import com.example.monywell.model.CustomerAccounts;
import com.example.monywell.modelValidator.*;
import com.example.monywell.repository.CustomerAccountsRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactonService {

    @Autowired
    private CustomerAccountsRepository customerAccountsRepository;

    @Transactional
    public ResponseEntity<Map<String, String>> withdrawFromAccount(Withdrawal withdrawal){
        Map<String, String> message = new HashMap<>();

        CustomerAccounts customerAccounts = customerAccountsRepository.findByAccountNumber(Long.parseLong(withdrawal.getCustomerAccountNumber())).orElseThrow(()-> new CustomerException("Customer with the account does not exist"));

        if(!customerAccounts.getCustomerId().isActive()){
            message.put("message", "Bad request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if(customerAccounts.getPin() != Long.parseLong(withdrawal.getSenderPin())){
            message.put("message", "Incorrect pin. Check and Try again");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if (customerAccounts.getBalance() < (withdrawal.getAmount() + Charges.getSAME_BANK_CHARGES())){
            message.put("message", "Insufficient Balance");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        customerAccounts.setBalance(customerAccounts.getBalance()-(withdrawal.getAmount()+Charges.getSAME_BANK_CHARGES()));
        customerAccountsRepository.save(customerAccounts);

        message.put("message", "Dear customer, a withdrawal of "+ withdrawal.getAmount() + " was successfully deducted from your account");

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @Transactional
    public ResponseEntity<Map<String, String>> creditAccount(Credit credit){
        Map<String, String> message = new HashMap<>();

        CustomerAccounts customerAccounts = customerAccountsRepository.findByAccountNumber(Long.parseLong(credit.getCustomerAccountNumber())).orElseThrow(
                ()->new CustomerException("Account number does not exist"));
        if(!customerAccounts.getCustomerId().isActive()){
            message.put("message", "Bad request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        customerAccounts.setBalance(customerAccounts.getBalance() + credit.getAmount());
        customerAccountsRepository.save(customerAccounts);
        Customer c = customerAccounts.getCustomerId();


        message.put("message", "Success");
        message.put("Transaction message", "Successfully deposited "+ credit.getAmount() + " into account with account number " +
                customerAccounts.getAccountNumber().toString().substring(0, 5) + "***");

        return ResponseEntity.ok(message);
    }
    @Transactional
    public ResponseEntity<Map<String, String>> transferFromAccount(Transfer transfer){

        Map<String, String> response = new HashMap<>();
        Withdrawal withdrawal = new Withdrawal(transfer.getSenderAccountNumber(),
                transfer.getAmount(), transfer.getSenderPin());
        HttpStatusCode status = withdrawFromAccount(withdrawal).getStatusCode();

        if (status == HttpStatusCode.valueOf(200)){

            Credit credit = new Credit(transfer.getReceiverAccountNumber(), transfer.getAmount());
            HttpStatusCode code = creditAccount(credit).getStatusCode();

            if(code == HttpStatusCode.valueOf(200)){
                response.put("message", "Transaction successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response.put("message", "Transaction Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
