package com.reloadly.customeraccount;


import com.reloadly.customeraccount.models.requests.CustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerAccountApplicationTests {

	@Test
	void contextLoads() {

	}


	@Test
	void Can_Create_Customer(){
		CustomerRequest user = CustomerRequest.builder()
				.firstName("Samson")
				.lastName("Oyetola")
				.phoneNumber("+2348064816493")
				.email("hello@samsonoyetola.com")
				.password("123456").build();

	}

}
