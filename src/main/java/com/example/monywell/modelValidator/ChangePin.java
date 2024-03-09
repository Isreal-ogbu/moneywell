package com.example.monywell.modelValidator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@RequiredArgsConstructor
public class ChangePin {
    @NotNull(message = "customer account number cannot be null")
    @NotBlank(message = "customer account number cannot be blank")
    @NotEmpty(message = "customer account number cannot be empty")
    @Length(min = 10, max = 10)
    private String customerAccountNumber;

    @NotNull(message = "pin number cannot be null")
    @NotBlank(message = "pin number cannot be blank")
    @NotEmpty(message = "pin number cannot be empty")
    @Length(min = 4, max = 4)
    private String customerPin;

    @NotNull(message = "pin number cannot be null")
    @NotBlank(message = "pin number cannot be blank")
    @NotEmpty(message = "pin number cannot be empty")
    @Length(min = 4, max = 4)
    private String customerNewPin;



}
