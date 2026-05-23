package com.task.CustomerManagementApplication.dto.request;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequestDto {

    @Email(message = "email format is invalid")
    @UniqueElements(message = "email is already exists")
    private String email;

    @Size(min = 2,max = 100, message = "the name must be between {min} and {max} characters")
    private String name;

    @Pattern(regexp = "^(\\+20|0020|0)(10|11|12|15)[0-9]{8}$", message = "phone number is invalid")
    @UniqueElements(message = "phone number has been used before")
    private String phone;

}
