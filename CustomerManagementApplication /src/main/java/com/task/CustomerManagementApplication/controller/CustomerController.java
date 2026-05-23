package com.task.CustomerManagementApplication.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.task.CustomerManagementApplication.CreateCustomerRequest;
import com.task.CustomerManagementApplication.UpdateCustomerRequest;
import com.task.CustomerManagementApplication.dto.request.CreateCustomerRequestDto;
import com.task.CustomerManagementApplication.dto.request.UpdateCustomerRequestDto;
import com.task.CustomerManagementApplication.dto.response.CustomerResponse;
import com.task.CustomerManagementApplication.entities.Customer;
import com.task.CustomerManagementApplication.mappers.CustomerMapper;
import com.task.CustomerManagementApplication.services.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequestDto requestDto) {
        CreateCustomerRequest request = customerMapper.toCreateCustomerRequest(requestDto);
        Customer customer = customerService.createCustomer(request);
        CustomerResponse response = customerMapper.toDto(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable long id) {
        Customer customer = customerService.findCustomerById(id);
        CustomerResponse response = customerMapper.toDto(customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            Pageable pageable) {
        Page<Customer> customers = customerService.search(name, email, phone, pageable);
        Page<CustomerResponse> responses = customers.map(customerMapper::toDto);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable long id,
            @Valid @RequestBody UpdateCustomerRequestDto requestDto) {
        UpdateCustomerRequest request = customerMapper.toUpdateCustomerRequest(requestDto);
        Customer updatedCustomer = customerService.updateCustomerById(id, request);
        CustomerResponse response = customerMapper.toDto(updatedCustomer);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
