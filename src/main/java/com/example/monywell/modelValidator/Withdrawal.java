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
public class Withdrawal {

    @NotNull(message = "customer account number cannot be null")
    @NotBlank(message = "customer account number cannot be blank")
    @NotEmpty(message = "customer account number cannot be empty")
    @Length(min = 9, max = 9)
    private String customerAccountNumber;

    @NotNull
    @DecimalMin(value = "1.0", message = "Amount must be valid")
    private Double amount;

    @NotNull(message = "pin number cannot be null")
    @NotBlank(message = "pin number cannot be blank")
    @NotEmpty(message = "pin number cannot be empty")
    @Length(min = 4, max = 4)
    private String senderPin;
}
