package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

class CustomerSenderTest {

    private RabbitTemplate rabbitTemplate;
    private Exchange exchange;
    private CustomerSender customerSender;

    @BeforeEach
    void setUp() {
        this.rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        this.exchange = Mockito.mock(Exchange.class);

        this.customerSender = new CustomerSender(this.rabbitTemplate, this.exchange);
    }

    @Test
    void createCustomer() {
        // Given
        Customer customer = Customer.builder().name("Ibrahima KANE")
                .email("irahima.kane@carrefour.com")
                .address(Address.builder().street("1 rue Antoine de Saint Exupery").zipCode("94270").country("FRANCE").build())
                .build();

        // When - Then
        assertThatCode(() -> this.customerSender.createCustomer(customer)).doesNotThrowAnyException();

        verify(this.rabbitTemplate).convertAndSend(this.exchange.getName(), "customer.routingkey", customer);
    }
}