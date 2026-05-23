package com.task.CustomerManagementApplication.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.task.CustomerManagementApplication.CreateCustomerRequest;
import com.task.CustomerManagementApplication.UpdateCustomerRequest;
import com.task.CustomerManagementApplication.entities.Customer;

public interface CustomerService {
    List<Customer> findCustomers();

    Customer findCustomerById(long id);
    
    Customer createCustomer(CreateCustomerRequest createCustomerRequest);

    void deleteCustomerById(long id);

    Customer updateCustomerById(long id, UpdateCustomerRequest updateCustomerRequest);

    Page<Customer> search(String name, String email, String phone, Pageable pageable);
    
}
