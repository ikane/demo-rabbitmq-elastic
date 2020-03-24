package com.example.demorabbitmqelastic.repository;

import com.example.demorabbitmqelastic.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
    Page<Customer> findCustomerByName(String name, Pageable pageable);
}
