package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Customer;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerSender {

    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    public CustomerSender(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void createCustomer(Customer customer) {
        String routingKey = "customer.created";
        this.rabbitTemplate.convertAndSend(this.exchange.getName(), routingKey, customer);
    }
}
