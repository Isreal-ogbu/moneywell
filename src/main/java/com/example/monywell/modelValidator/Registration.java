package com.example.monywell.modelValidator;

import com.example.monywell.model.model_enum.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Registration {
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "phone number cannot be null")
    @NotBlank(message = "phone number cannot be blank")
    @NotEmpty(message = "phone number cannot be empty")
    @Pattern(regexp = "^\\+234\\d{10}$",  message = "Enter a valid phone number")
    private String mobile;

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "must enter a valid email")
    private String email;

    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    @NotEmpty(message = "username cannot be empty")
    private String userName;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    @NotEmpty(message = "password cannot be empty")
    @Length(min = 8, max = 50, message = "min length of password is 8")
    private String password;

    @NotNull(message = "address cannot be null")
    @NotBlank(message = "address cannot be blank")
    @NotEmpty(message = "address cannot be empty")
    private String address;

    @NotNull(message = "pin cannot be null")
    @NotBlank(message = "pin cannot be blank")
    @NotEmpty(message = "pin cannot be empty")
    @Length(min = 4, max = 4)
    private String pin;

    @Enumerated(value = EnumType.STRING)
    private AccountType account_type;

}
