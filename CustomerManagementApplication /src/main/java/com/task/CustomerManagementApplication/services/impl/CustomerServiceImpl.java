package com.task.CustomerManagementApplication.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.task.CustomerManagementApplication.CreateCustomerRequest;
import com.task.CustomerManagementApplication.UpdateCustomerRequest;
import com.task.CustomerManagementApplication.entities.Customer;
import com.task.CustomerManagementApplication.repository.CustomerRepository;
import com.task.CustomerManagementApplication.services.CustomerService;
import com.task.CustomerManagementApplication.specification.CustomerSpecification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    @Override
    @Transactional
    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer newCustomer = new Customer();
        newCustomer.setEmail(createCustomerRequest.getEmail());
        newCustomer.setName(createCustomerRequest.getName());
        newCustomer.setPhone(createCustomerRequest.getPhone());

        return customerRepository.save(newCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomerById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Customer updateCustomerById(long id, UpdateCustomerRequest updateCustomerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        if (updateCustomerRequest.getEmail() != null && !updateCustomerRequest.getEmail().isBlank()) {
            existingCustomer.setEmail(updateCustomerRequest.getEmail());
        }
        if (updateCustomerRequest.getName() != null && !updateCustomerRequest.getName().isBlank()) {
            existingCustomer.setName(updateCustomerRequest.getName());
        }
        if (updateCustomerRequest.getPhone() != null && !updateCustomerRequest.getPhone().isBlank()) {
            existingCustomer.setPhone(updateCustomerRequest.getPhone());
        }

        return customerRepository.save(existingCustomer);
    }

    @Override
    public Page<Customer> search(String name, String email, String phone, Pageable pageable) {
        return customerRepository.findAll(
                CustomerSpecification.filterByNameEmailPhone(name, email, phone),
                pageable);
    }
}
