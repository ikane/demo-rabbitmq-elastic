package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = CustomerServiceIT.Initializer.class)
@Testcontainers
class CustomerServiceIT {

    public static final int RABBITMQ_PORT = 5672;

    @Container
    public static GenericContainer rabbitMq = new GenericContainer("rabbitmq:3.7-management")
                                                        .withExposedPorts(RABBITMQ_PORT);

    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomer() {
        // Given
        Customer customer = Customer.builder().name("Ibrahima KANE")
                .email("irahima.kane@carrefour.com")
                .address(Address.builder().street("1 rue Antoine de Saint Exupery").zipCode("94270").country("FRANCE").build())
                .build();

        // When
        customerService.createCustomer(customer);

        // Then

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