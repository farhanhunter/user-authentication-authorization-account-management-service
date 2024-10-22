package com.example.userfinance.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestDto {

    @NotBlank(message = "Account Type cannot be blank")
    private String accountType;

    @NotBlank(message = "Account Number cannot be blank")
    private String accountNumber;

    private Double balance;
}
