package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(initializers = CustomerSenderIT.Initializer.class)
@Testcontainers
class CustomerSenderIT {

    public static final int RABBITMQ_PORT = 5672;

    @Container
    public static GenericContainer rabbitMq = new GenericContainer("rabbitmq:3.7-management")
                                                        .withExposedPorts(RABBITMQ_PORT);

    @Autowired
    private CustomerSender customerSender;

    @Autowired
    //@SpyBean
    private CustomerListener customerListener;

    @Test
    void createCustomer() {
        // Given
        Customer customer = Customer.builder().name("Ibrahima KANE")
                .email("irahima.kane@carrefour.com")
                .address(Address.builder().street("1 rue Antoine de Saint Exupery").zipCode("94270").country("FRANCE").build())
                .build();

        LogCaptor<CustomerListener> logCaptor = LogCaptor.forClass(CustomerListener.class);

        // When
        customerSender.createCustomer(customer);

        // Then
        //verify(customerListener).receive(any());
        //assertThat(logCaptor.getLogs("info")).containsExactly(expectedInfoMessage);
        //assertThat(logCaptor.getLogs("info")).isNotEmpty();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues propertyValues = TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbitMq.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbitMq.getMappedPort(RABBITMQ_PORT)
            );

            propertyValues.applyTo(configurableApplicationContext);
        }
    }
}