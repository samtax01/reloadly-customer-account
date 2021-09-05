package com.reloadly.customeraccount.controllers;

import com.reloadly.customeraccount.Seeder;
import com.reloadly.customeraccount.helpers.PasswordHelper;
import com.reloadly.customeraccount.helpers.Response;
import com.reloadly.customeraccount.repositories.interfaces.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ICustomerRepository iCustomerRepository;

    @MockBean
    private PasswordHelper passwordHelper;

    @BeforeEach
    public void setUp() {
        // webTestClient = webTestClient.mutate().responseTimeout(Duration.ofMillis(30000)).build();
    }

    @Test
    void canCreateCustomer(){
        // Arrange
        // Act
        webTestClient
                .post()
                .uri("/api/v1/customers")
                .body(BodyInserters.fromValue(Seeder.getCustomerRequest()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Response.class)
                .value(response -> {
                    log.info("\nResponse is {}", response);

                    // Assert
                    Assertions.assertTrue(response.isStatus());
                });
    }


    @Test
    void canUpdateCustomer(){
        // Arrange
        var customer = Seeder.getCustomer();
        customer.setId(1);
        Mockito.doReturn(Optional.of(customer)).when(iCustomerRepository).findById(Mockito.any());

        // Act
        webTestClient
                .put()
                .uri("/api/v1/customers/" + customer.getId())
                .body(BodyInserters.fromValue(Seeder.getCustomerRequest()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Response.class)
                .value(response -> {
                    log.info("\nResponse is {}", response);

                    // Assert
                    Assertions.assertTrue(response.isStatus());
                });
    }


    @Test
    void customerNotFound(){
        // Arrange
        // Act
        webTestClient
                .get()
                .uri("/api/v1/customers/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(Response.class)
                .value(response -> {
                    log.info("\nResponse is {}", response);

                    // Assert
                    Assertions.assertFalse(response.isStatus());
                });
    }

    @Test
    void getCustomer(){
        // Arrange
        var customer = Seeder.getCustomer();
        customer.setId(1);
        Mockito.doReturn(Optional.of(customer)).when(iCustomerRepository).findById(Mockito.any());

        // Act
        webTestClient
                .get()
                .uri("/api/v1/customers/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Response.class)
                .value(response -> {
                    log.info("\nResponse is {}", response);

                    // Assert
                    Assertions.assertTrue(response.isStatus());
                });
    }


    @Test
    void canLogin(){
        // Arrange
        var customer = Seeder.getCustomer();
        customer.setId(1);
        customer.setPassword("123456");
        Mockito.doReturn(customer).when(iCustomerRepository).findByEmail(Mockito.any());
        Mockito.doReturn(true).when(passwordHelper).matches("123456", customer.getPassword());

        // Act
        webTestClient
                .post()
                .uri("/api/v1/auth/login")
                .body(BodyInserters.fromValue(Seeder.getCustomerLoginRequest()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Response.class)
                .value(response -> {
                    log.info("\nResponse is {}", response);

                    // Assert
                    Assertions.assertTrue(response.isStatus());
                });
    }


}
