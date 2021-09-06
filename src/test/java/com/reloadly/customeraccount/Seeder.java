package com.reloadly.customeraccount;

import com.reloadly.customeraccount.models.Customer;
import com.reloadly.customeraccount.models.requests.CustomerLoginRequest;
import com.reloadly.customeraccount.models.requests.CustomerRequest;
import com.reloadly.customeraccount.models.requests.EmailRequest;
import com.reloadly.customeraccount.models.responses.CustomerResponse;
import org.modelmapper.ModelMapper;

public class Seeder {


    public static CustomerRequest getCustomerRequest(){
        return CustomerRequest
                .builder()
                .firstName("Samson")
                .lastName("Oyetola")
                .phoneNumber("+2348064816493")
                .email("hello@samsonoyetola.com")
                .password("123456")
                .build();
    }

    public static Customer getCustomer(){
        return new ModelMapper().map(getCustomerRequest(), Customer.class);
    }

    public static CustomerResponse getCustomerResponse(){
        return new ModelMapper().map(getCustomer(), CustomerResponse.class);
    }

    public static EmailRequest getEmailRequest(){
        return EmailRequest
                .builder()
                .toEmail("hello@samsonoyetola.com")
                .fromEmail("notification@reloadly.com")
                .build();
    }


    public static CustomerLoginRequest getCustomerLoginRequest() {
        var login =  new CustomerLoginRequest();
        login.setEmail("hello@samsonoyetola.com");
        login.setPassword("123456");
        return login;
    }
}
