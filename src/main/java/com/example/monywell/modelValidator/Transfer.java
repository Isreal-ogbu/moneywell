package com.example.monywell.modelValidator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Data
public class Transfer {
    @NotNull(message = "customer account number cannot be null")
    @NotBlank(message = "customer account number cannot be blank")
    @NotEmpty(message = "customer account number cannot be empty")
    @Length(min = 9, max = 9, message="Account number is incorrect. Enter a valid account number")
    private String senderAccountNumber;

    @NotNull(message = "customer account number cannot be null")
    @NotBlank(message = "customer account number cannot be blank")
    @NotEmpty(message = "customer account number cannot be empty")
    @Length(min = 9, max = 9, message="Account number is incorrect. Enter a valid account number")
    private String receiverAccountNumber;

    @NotNull
    @DecimalMin(value = "1.0", message = "Value must be at least 1.0")
    private Double amount;

    @NotNull(message = "pin number cannot be null")
    @NotBlank(message = "pin number cannot be blank")
    @NotEmpty(message = "pin number cannot be empty")
    @Length(min = 4, max = 4)
    private String senderPin;

    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be blank")
    @NotEmpty(message = "description cannot be empty")
    private String description;

    
}
