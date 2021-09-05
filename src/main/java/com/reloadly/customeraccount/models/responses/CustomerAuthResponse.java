package com.reloadly.customeraccount.models.responses;

import com.reloadly.customeraccount.helpers.AuthorisationHelper;
import com.reloadly.customeraccount.models.Customer;
import lombok.Data;

@Data
public class CustomerAuthResponse extends Customer {
    private AuthorisationHelper.TokenPayload token;
}
