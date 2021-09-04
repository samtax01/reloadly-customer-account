package com.reloadly.customeraccount.repositories;

import com.reloadly.customeraccount.helpers.CustomException;
import com.reloadly.customeraccount.models.Customer;
import com.reloadly.customeraccount.models.requests.CustomerRequest;
import com.reloadly.customeraccount.models.responses.CustomerResponse;
import com.reloadly.customeraccount.repositories.interfaces.ICustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepository{

    private final ICustomerRepository iCustomerRepository;

    private final ModelMapper modelMap;

    public CustomerRepository(ICustomerRepository iCustomerRepository, ModelMapper modelMap) {
        this.iCustomerRepository = iCustomerRepository;
        this.modelMap = modelMap;
    }


    public Mono<CustomerResponse> Create(CustomerRequest request) throws CustomException {

        if(iCustomerRepository.findByEmail(request.getEmail()) != null)
            throw new CustomException("Email already exists", HttpStatus.BAD_REQUEST);

        Customer customer = modelMap.map(request, Customer.class);

        try{
            iCustomerRepository.saveAndFlush(customer);

            return Mono.just(modelMap.map(customer, CustomerResponse.class));
        }catch (Exception ex){
            throw new CustomException("Unable to create profile. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
