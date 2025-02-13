package com.bank.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDetailsDto {

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 3, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDTO accountsDTO;
    private LoansDto loansDto;
    private CardsDto cardsDto;

}
