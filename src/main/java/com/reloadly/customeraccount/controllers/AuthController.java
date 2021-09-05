package com.reloadly.customeraccount.controllers;

import com.reloadly.customeraccount.helpers.Response;
import com.reloadly.customeraccount.helpers.Validator;
import com.reloadly.customeraccount.models.requests.CustomerLoginRequest;
import com.reloadly.customeraccount.models.responses.CustomerAuthResponse;
import com.reloadly.customeraccount.repositories.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CustomerRepository repository;

    public AuthController(CustomerRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @Operation(summary = "Login with email and password to get a JWT")
    @PostMapping(path = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response<CustomerAuthResponse>>> login(@RequestBody CustomerLoginRequest request){
        Validator.validate(request);
        return repository.login(request).map(x-> ResponseEntity.ok(Response.success(x)));
    }


}
