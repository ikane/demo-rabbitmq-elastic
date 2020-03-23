package com.example.demorabbitmqelastic;
import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = CustomerElasticIT.Initializer.class)
public class CustomerElasticIT {

    @Container
    public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("elasticsearch:7.6.1")
            .withExposedPorts(9200);

    /*
    GenericContainer container = new GenericContainer("docker.elastic.co/elasticsearch/elasticsearch:6.1.1")
         .withEnv("discovery.type", "single-node")
         .withExposedPorts(9200)
         .waitingFor(
           Wait
           .forHttp("/_cat/health?v&pretty")
           .forStatusCode(200)
         );
     */


    //@Autowired
    //private CustomerService customerService;

    @Test
    public void testSaveCustomer() {
        // GIVEN
        Customer customer = Customer.builder().name("Ibrahima KANE")
                .email("irahima.kane@carrefour.com")
                .address(Address.builder().street("1 rue Antoine de Saint Exupery").zipCode("94270").country("FRANCE").build())
                .build();

        // WHEN


        // THEN

    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "elasticsearch.host=" + elasticsearchContainer.getHttpHostAddress(),
                    "elasticsearch.port=" + elasticsearchContainer.getMappedPort(9200)
            ).applyTo(context);
        }
    }

}
