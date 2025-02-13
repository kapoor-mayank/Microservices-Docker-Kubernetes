package com.bank.accounts.controller;


import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.AccountsContactInfoDto;
import com.bank.accounts.dto.CustomerDTO;
import com.bank.accounts.dto.ResponseDTO;
import com.bank.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private final IAccountsService iAccountsService;
    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;
    @PostMapping( "/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {

        iAccountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam
                                                            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        CustomerDTO customerDTO = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        boolean isUpdated = iAccountsService.updateAccount(customerDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }
    @GetMapping("/build-info")
    public ResponseEntity<ResponseDTO> getBuildVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(AccountsConstants.STATUS_200, buildVersion));
    }

    @GetMapping("/java-version")
    public ResponseEntity<ResponseDTO> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(AccountsConstants.STATUS_200,
                        environment.getProperty("JAVA_HOME")));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}
