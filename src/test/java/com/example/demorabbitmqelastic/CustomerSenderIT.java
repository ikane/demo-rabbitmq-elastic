package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(initializers = CustomerSenderIT.Initializer.class)
class CustomerSenderIT  extends AbstractIntegrationTest {

    @Autowired
    private CustomerSender customerSender;

    //@Autowired
    @SpyBean
    private CustomerListener customerListener;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void createCustomer() throws InterruptedException {
        // Given
        Customer customer = Customer.builder().name("Ibrahima KANE")
                .email("irahima.kane@carrefour.com")
                .address(Address.builder().street("1 rue Antoine de Saint Exupery").zipCode("94270").country("FRANCE").build())
                .build();

        LogCaptor<CustomerListener> logCaptor = LogCaptor.forClass(CustomerListener.class);

        // When
        //customerSender.createCustomer(customer);
        rabbitTemplate.convertAndSend("customer.created", customer);

        //Thread.sleep(1000);

        // Then
        verify(customerListener, timeout(1000)).receive(any());
        //assertThat(logCaptor.getLogs("info")).containsExactly(expectedInfoMessage);
        //assertThat(logCaptor.getLogs("info")).isNotEmpty();
    }
}