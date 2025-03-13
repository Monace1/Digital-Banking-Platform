package com.app.Account_Service.Repository;

import com.app.Account_Service.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNationalid(String nationalid);
    boolean existsByNationalid(String nationalid);
    void deleteByNationalid(String nationalid);
}
