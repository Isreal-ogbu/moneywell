package com.example.monywell.services;

import com.example.monywell.exception.CustomerException;
import com.example.monywell.model.Customer;
import com.example.monywell.model.CustomerAccounts;
import com.example.monywell.modelValidator.AccountNumberValidator;
import com.example.monywell.modelValidator.ChangeCustomerDetails;
import com.example.monywell.modelValidator.ChangePin;
import com.example.monywell.modelValidator.Registration;
import com.example.monywell.repository.CustomerAccountsRepository;
import com.example.monywell.repository.CustomerRepository;

import com.example.monywell.utils.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Configuration
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    private CustomerAccountsRepository customerAccountsRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Transactional
    public ResponseEntity<Map<String, String>> createCustomerAccount(Registration e) {
        Map<String, String> response = new HashMap<>();

        Optional<Customer> c = customerRepository.findByEmail(e.getEmail());

        if (c.isPresent() && c.get().getNoOfAccounts() >= 5){
            response.put("message", "Maximum account opening limit reached");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (c.isPresent()){

            Customer customer = c.get();
            customer.setNoOfAccounts(c.get().getNoOfAccounts()+1);
            customerRepository.save(customer);

            CustomerAccounts customerAccounts = new CustomerAccounts(c.get(), AccountNumberGenerator.getAccountNumber(), e.getPin(), e.getAccount_type());
            customerAccountsRepository.save(customerAccounts);
            response.put("message", "Welcome to MonyWell!, Your account has successfully been created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        Customer customer = new Customer(e.getName(), e.getMobile(), e.getEmail(), e.getUserName(), e.getPassword(), e.getAddress());
        Customer customer1 = customerRepository.save(customer);

        CustomerAccounts customerAccounts = new CustomerAccounts(customer1, AccountNumberGenerator.getAccountNumber(), e.getPin(), e.getAccount_type());
        customerAccountsRepository.save(customerAccounts);

        response.put("message", "Welcome to MonyWell!, Your account has successfully been created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Transactional
    public ResponseEntity<Map<String, String>> updateAccountInformation(Long id, ChangeCustomerDetails customer){
        Map<String, String> details = new HashMap<>();

        Customer customerOldDetails = customerRepository.findById(id).orElseThrow(()-> new CustomerException("Customer with id does nor exist"));
        customerOldDetails.setAddress(customer.getAddress());
        customerOldDetails.setName(customer.getEmail());
        customerOldDetails.setMobile(customer.getMobile());
        customerOldDetails.setEmail(customer.getEmail());
        customerOldDetails.setUserName(customer.getUserName());

        customerRepository.save(customerOldDetails);

        details.put("message", "Customer details updated");

        return ResponseEntity.status(HttpStatus.OK).body(details);
    }
    public ResponseEntity<Map<String, String>> getAccount(Long id){

        Customer customer = customerRepository.findById(id).orElseThrow(()->new CustomerException("Customer with id does nor exist"));

        return ResponseEntity.status(HttpStatus.OK).body(customer.getDetail());
    }

    @Transactional
    public ResponseEntity<Map<String, String>> changePin(ChangePin changePin ) {

        Map<String, String > response = new HashMap<>();
        CustomerAccounts customerAccounts = customerAccountsRepository.findByAccountNumber(Long.parseLong(changePin.getCustomerAccountNumber()))
                .orElseThrow(() -> new CustomerException("Customer accounts not found"));
        if (!customerAccounts.getPin().equals(Long.parseLong(changePin.getCustomerPin()))) {
            response.put("message", "Incorrect current PIN. Please try again." );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        customerAccounts.setPin(Long.valueOf(changePin.getCustomerNewPin()));
        customerAccountsRepository.save(customerAccounts);
        response.put("message", "PIN successfully changed.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Map<String, String>> deleteAccount(AccountNumberValidator number){
        Map<String, String> message = new HashMap<>();

        Customer customerOldDetails = customerRepository.findById(Long.parseLong(number.getCustomerAccountNumber())).orElseThrow(()-> new CustomerException("Customer with id does nor exist"));
        if (!customerOldDetails.isActive()){
            message.put("message", "Incorrect pin. Check and Try again");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

        }
        customerOldDetails.setActive(true);
        customerRepository.save(customerOldDetails);

        message.put("message", "Account deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    public ResponseEntity<Map<String, Map<String, String>>> getAccountByEmail(String email){

        Map<String, Map<String, String>> message = new HashMap<>();
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new CustomerException("Customer with email does nor exist"));
        List<CustomerAccounts> customerAccounts = customerAccountsRepository.findByCustomerId(customer).orElseThrow(()-> new CustomerException("Customer does not exist"));

        message.put("Cusomer-details", customer.getDetail());
        for (CustomerAccounts customerAccounts1: customerAccounts){
            message.put("Account"+customerAccounts.indexOf(customerAccounts1), customerAccounts1.getDetail());
        }

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
