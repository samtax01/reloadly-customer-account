package com.reloadly.customeraccount.repositories;

import com.reloadly.customeraccount.enums.Role;
import com.reloadly.customeraccount.helpers.CustomException;
import com.reloadly.customeraccount.helpers.HttpRequest;
import com.reloadly.customeraccount.helpers.AuthorisationHelper;
import com.reloadly.customeraccount.helpers.PasswordHelper;
import com.reloadly.customeraccount.models.Customer;
import com.reloadly.customeraccount.models.requests.CustomerLoginRequest;
import com.reloadly.customeraccount.models.requests.CustomerRequest;
import com.reloadly.customeraccount.models.requests.EmailRequest;
import com.reloadly.customeraccount.models.responses.CustomerAuthResponse;
import com.reloadly.customeraccount.models.responses.CustomerResponse;
import com.reloadly.customeraccount.repositories.interfaces.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CustomerRepository{

    private final ICustomerRepository iCustomerRepository;
    private final ModelMapper modelMap;
    private final PasswordHelper passwordHelper;
    private final AuthorisationHelper authorisationHelper;


    public CustomerRepository(
            ICustomerRepository iCustomerRepository,
            ModelMapper modelMap,
            PasswordHelper passwordHelper,
            AuthorisationHelper authorisationHelper
    ) {
        this.iCustomerRepository = iCustomerRepository;
        this.modelMap = modelMap;
        this.passwordHelper = passwordHelper;
        this.authorisationHelper = authorisationHelper;
    }


    /**
     * Get single customer
     */
    public CustomerResponse get(Long id) throws CustomException{
        var customer = iCustomerRepository.findById(id);
        if(customer.isEmpty())
            throw new CustomException("Customer not found", HttpStatus.NOT_FOUND);
        return modelMap.map(customer.get(), CustomerResponse.class);
    }


    /**
     * Delete customer
     */
    public CustomerResponse delete(Long id) throws CustomException{
        var customer = iCustomerRepository.findById(id);
        if(customer.isEmpty())
            throw new CustomException("Customer not found", HttpStatus.NOT_FOUND);
        iCustomerRepository.delete(customer.get());
        return modelMap.map(customer.get(), CustomerResponse.class);
    }



    /**
     * Get all customers
     */
    public List<CustomerResponse> gets(){
        return iCustomerRepository.findAll().stream().map(x-> modelMap.map(x, CustomerResponse.class) ).collect(Collectors.toList());
    }



    /**
     * Create Customer
     */
    public Mono<CustomerResponse> create(CustomerRequest request) throws CustomException {
        if(iCustomerRepository.findByEmail(request.getEmail()) != null)
            throw new CustomException("Email already exists", HttpStatus.BAD_REQUEST);
        Customer customer = modelMap.map(request, Customer.class);
        customer.setRoles(Role.USER.name());
        customer.setPassword( passwordHelper.encode(request.getPassword()) );
        try{
            // Save Customer
            iCustomerRepository.saveAndFlush(customer);

            // Send Mail to Customer in the background
            if(customer.getId() > 0){
                sendWelcomeEmail(customer.getEmail());
            }

            return Mono.just(modelMap.map(customer, CustomerResponse.class));
        }catch (Exception ex){
            throw new CustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Update Customer
     */
    public CustomerResponse update(long id, CustomerRequest request) throws CustomException {
        var customer = iCustomerRepository.findById(id);
        if(customer.isEmpty())
            throw new CustomException("Customer not found", HttpStatus.NOT_FOUND);
        var existingCustomer = customer.get();

        existingCustomer.setFirstName(request.getFirstName());
        existingCustomer.setLastName(request.getLastName());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        existingCustomer.setPassword( passwordHelper.encode(request.getPassword()) );
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        iCustomerRepository.saveAndFlush(existingCustomer);
        return modelMap.map(existingCustomer, CustomerResponse.class);
    }


    /**
     * Login
     */
    public CustomerAuthResponse login(CustomerLoginRequest request) throws CustomException {
        // get user
        var customer = iCustomerRepository.findByEmail(request.getEmail());

        // validate
        if(customer == null || !passwordHelper.matches(request.getPassword(), customer.getPassword()))
            throw new CustomException("Email/Password not valid", HttpStatus.UNAUTHORIZED);
        var response = modelMap.map(customer, CustomerAuthResponse.class);

        // Generate Token
        response.setToken(authorisationHelper.generateJwt(customer.getEmail(), customer.getRoles()));
        return response;
    }



    /**
     * Send Background Email Notification
     */
    @Async
    public void sendWelcomeEmail(String customerEmail){
        var emailRequest = EmailRequest.builder()
                .email(customerEmail)
                .subject("Welcome to Reloadly")
                .body("Click <a href='#'>here</a> to validate your email. <br/>yada yada yada yada")
                .build();
        try{
            HttpRequest.make("http://localhost:8082/trips", HttpMethod.POST, emailRequest, String.class).subscribe( x->log.info("Email sent successfully. " + x) );
        }catch (Exception ex){
            log.error("Unable to send email. " + ex.getMessage());
        }
    }


}
