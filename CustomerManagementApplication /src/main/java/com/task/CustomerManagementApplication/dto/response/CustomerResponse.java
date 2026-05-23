package com.task.CustomerManagementApplication.dto.response;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data   
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private long id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}