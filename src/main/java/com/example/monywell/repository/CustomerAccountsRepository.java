package com.example.monywell.repository;

import com.example.monywell.model.Customer;
import com.example.monywell.model.CustomerAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerAccountsRepository extends JpaRepository<CustomerAccounts, Long> {
    Optional<CustomerAccounts> findByAccountNumber(Long accountNumber);
    Optional<List<CustomerAccounts>>findByCustomerId(Customer customer);
}
