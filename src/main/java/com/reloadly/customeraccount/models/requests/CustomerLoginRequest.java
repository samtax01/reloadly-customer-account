package com.reloadly.customeraccount.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CustomerLoginRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
