package com.example.demorabbitmqelastic;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.util.stream.Stream;

public abstract class AbstractIntegrationTest {
    public static final int RABBITMQ_PORT = 5672;

    public static final ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("elasticsearch:7.6.1");
    public static final GenericContainer rabbitMq = new GenericContainer("rabbitmq:3.7-management").withExposedPorts(RABBITMQ_PORT);

    static {
        //Stream.of(elasticsearchContainer, rabbitMq).parallel().forEach(genericContainer -> genericContainer.start());
        elasticsearchContainer.start();
        rabbitMq.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            /*
            TestPropertyValues.of(
                    "elasticsearch.host=" + elasticsearchContainer.getHttpHostAddress()
            ).applyTo(context);
            */
            TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbitMq.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbitMq.getMappedPort(RABBITMQ_PORT),
                    "elasticsearch.host=" + elasticsearchContainer.getContainerIpAddress(),
                    "elasticsearch.port=" + elasticsearchContainer.getMappedPort(9200)
            ).applyTo(context);

        }
    }
}
