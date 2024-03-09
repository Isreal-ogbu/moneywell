package com.example.monywell.controllers;

import com.example.monywell.modelValidator.*;
import com.example.monywell.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Map<String, String>> getCustomerInformation(@PathVariable Long id){

        return customerService.getAccount(id);
    }
    @GetMapping("/get-user/{email}")
    public ResponseEntity<Map<String, Map<String, String>>> getCustomerInformationByEmail(@PathVariable String email){

        return customerService.getAccountByEmail(email);
    }

    @PostMapping("/change-pin/")
    public ResponseEntity<Map<String, String>> changePin(@RequestBody @Valid ChangePin changePin) {

        return customerService.changePin(changePin);
    }

    @PostMapping("/create-account/")
    public ResponseEntity<Map<String, String>> createAccount(@RequestBody @Valid Registration registration){
        return customerService.createCustomerAccount(registration);
    }

    @PutMapping("/update-account/{customerId}")
    public ResponseEntity<Map<String, String>> updateAccountDetails(@PathVariable @Valid long customerId, @RequestBody @Valid ChangeCustomerDetails details){
        return customerService.updateAccountInformation(customerId, details);
    }

    @PostMapping("/delete-account/")
    public ResponseEntity<Map<String, String>> createAccount(@RequestBody @Valid AccountNumberValidator number){
        return customerService.deleteAccount(number);
    }
}
