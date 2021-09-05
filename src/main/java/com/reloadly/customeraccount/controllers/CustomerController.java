package com.reloadly.customeraccount.controllers;

import com.reloadly.customeraccount.helpers.Response;
import com.reloadly.customeraccount.helpers.Validator;
import com.reloadly.customeraccount.models.requests.CustomerRequest;
import com.reloadly.customeraccount.models.responses.CustomerResponse;
import com.reloadly.customeraccount.repositories.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

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
        return repository.create(request).map(x-> ResponseEntity.ok(Response.success(x)));
    }


    @SneakyThrows
    @Operation(summary = "Update customer information")
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Updated", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",  content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) })
    })
    public ResponseEntity<Response<CustomerResponse>> updateCustomer(@PathVariable long id, @RequestBody CustomerRequest request) {
        Validator.validate(request);
        return ResponseEntity.ok(Response.success(repository.update(id, request)));
    }


    @SneakyThrows
    @Operation(summary = "Get a single customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the item", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "Item not found",  content = @Content)
    })
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response<CustomerResponse>>> getCustomer(@PathVariable final long id){
        return Mono.just(ResponseEntity.ok(Response.success(repository.get(id))));
    }


    @SneakyThrows
    @Operation(summary = "Delete customer information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "Item not found",  content = @Content)
    })
    @DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<CustomerResponse>> deleteCustomer(@PathVariable final long id){
        return ResponseEntity.ok(Response.success(repository.delete(id)));
    }


    @SneakyThrows
    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item list", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<List<CustomerResponse>>> getCustomers(){
        return ResponseEntity.ok(Response.success(repository.gets()));
    }

}
