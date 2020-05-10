package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.findCustomers();

	}

	@PostMapping("/customers")
	public Customer saveCustomers(@RequestBody Customer customer) {
		return customerService.createCustomer(customer);
	}

	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		return customerService.updateCustomer(customer);

	}

	@GetMapping("/aggregates/gender")
	public Object getGenderAggregates() {
		return customerService.getCustomerAggregateByGender();
	}

	@GetMapping("/marriedFemaleWithoutAddress")
	public Object getMarriedFemaleWithoutAddress() {
		return customerService.getMarriedFemaleWithoutAddress();

	}
}
