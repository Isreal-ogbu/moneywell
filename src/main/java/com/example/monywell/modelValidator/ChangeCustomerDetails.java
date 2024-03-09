package com.example.monywell.modelValidator;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ChangeCustomerDetails {
    private String name;
    private String mobile;
    private String email;
    private String userName;
    private String password;
    private String address;

}
