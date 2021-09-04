package com.reloadly.customeraccount.controllers;

import com.reloadly.customeraccount.helpers.Response;
import com.reloadly.customeraccount.helpers.Validator;
import com.reloadly.customeraccount.models.requests.CustomerLoginRequest;
import com.reloadly.customeraccount.models.requests.CustomerRequest;
import com.reloadly.customeraccount.models.responses.CustomerResponse;
import com.reloadly.customeraccount.repositories.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.awt.print.Book;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @Operation(summary = "Create a new customer")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer Created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",  content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) })
    })
    public Mono<ResponseEntity<Response<CustomerResponse>>> createCustomer(@RequestBody CustomerRequest request) {
        Validator.validate(request);
        return repository.Create(request).map(x-> ResponseEntity.ok(Response.success(x)));
    }




}
