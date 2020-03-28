package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Customer;
import com.example.demorabbitmqelastic.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public Customer createCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> findCustomers() {
        List<Customer> result = new ArrayList<>();
        this.customerRepository.findAll().forEach(customer -> result.add(customer));
        return result;
    }
}
