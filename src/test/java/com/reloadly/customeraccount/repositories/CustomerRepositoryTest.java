package com.reloadly.customeraccount.repositories;

import com.reloadly.customeraccount.Seeder;
import com.reloadly.customeraccount.helpers.CustomException;
import com.reloadly.customeraccount.repositories.interfaces.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerRepositoryTest {


    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private ICustomerRepository iCustomerRepository;


    @Test
    void canCreateCustomer() throws CustomException {
        // Arrange
        // Act
        var customer = customerRepository.create(Seeder.getCustomerRequest());

        StepVerifier
                .create(customer)
                .expectNextMatches(response ->
                        // Assert
                        !Objects.isNull(response))
                .verifyComplete();
    }


    @Test
    void canUpdateCustomer() throws CustomException {
        // Arrange
        var existingCustomer = Seeder.getCustomer();
        existingCustomer.setId(1);
        Mockito.doReturn(Optional.of(existingCustomer)).when(iCustomerRepository).findById(Mockito.any());

        // Act
        var customer = customerRepository.update(1L, Seeder.getCustomerRequest());
        StepVerifier
                .create(customer)
                .expectNextMatches(response ->
                        // Assert
                        response.getId() == existingCustomer.getId() )
                .verifyComplete();
    }


    @Test
    void getCustomer() throws CustomException {
        // Arrange
        var existingCustomer = Seeder.getCustomer();
        existingCustomer.setId(1);
        Mockito.doReturn(Optional.of(existingCustomer)).when(iCustomerRepository).findById(Mockito.any());

        // Act
        var customer = customerRepository.get(1L);
        StepVerifier
                .create(customer)
                .expectNextMatches(response ->
                        // Assert
                        response.getId() == existingCustomer.getId() )
                .verifyComplete();
    }






}
