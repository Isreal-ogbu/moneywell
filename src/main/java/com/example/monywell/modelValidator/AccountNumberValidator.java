package com.example.monywell.modelValidator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountNumberValidator {
    @NotNull(message = "customer account number cannot be null")
    @NotBlank(message = "customer account number cannot be blank")
    @NotEmpty(message = "customer account number cannot be empty")
    @Length(min = 9, max = 9, message="Account number is incorrect. Enter a valid account number")
    private String customerAccountNumber;
}
