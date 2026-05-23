package com.task.CustomerManagementApplication.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.CustomerManagementApplication.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{  
     Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);
}
