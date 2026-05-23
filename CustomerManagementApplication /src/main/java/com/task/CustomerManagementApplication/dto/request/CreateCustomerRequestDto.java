package com.task.CustomerManagementApplication.dto.request;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateCustomerRequestDto {

    @NotNull(message = "email is required")
    @Email(message = "email format is invalid")
    private String email;
    
    @NotNull(message = "name is required")
    @NotBlank(message = "name is required")
    @Size(min = 2,max = 100, message = "the name must be between {min} and {max} characters")
    private String name;
    
    @NotNull(message = "Phone is required")
    @Pattern(regexp = "^(\\+20|0020|0)(10|11|12|15)[0-9]{8}$", message = "phone number is invalid")
    private String phone;

}
