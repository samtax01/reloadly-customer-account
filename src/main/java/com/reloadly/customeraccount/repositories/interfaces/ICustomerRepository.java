package com.reloadly.customeraccount.repositories.interfaces;

import com.reloadly.customeraccount.models.Customer;
import com.reloadly.customeraccount.models.responses.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);


}
