package com.reloadly.customeraccount;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAccountApplication.class, args);
	}

}


