package com.task.CustomerManagementApplication.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.task.CustomerManagementApplication.CreateCustomerRequest;
import com.task.CustomerManagementApplication.UpdateCustomerRequest;
import com.task.CustomerManagementApplication.dto.request.CreateCustomerRequestDto;
import com.task.CustomerManagementApplication.dto.request.UpdateCustomerRequestDto;
import com.task.CustomerManagementApplication.dto.response.CustomerResponse;
import com.task.CustomerManagementApplication.entities.Customer;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerResponse toDto(Customer customer); 

    CreateCustomerRequest toCreateCustomerRequest(CreateCustomerRequestDto createCustomerRequest); 
    
    UpdateCustomerRequest toUpdateCustomerRequest(UpdateCustomerRequestDto updateCustomerRequest); 

}
