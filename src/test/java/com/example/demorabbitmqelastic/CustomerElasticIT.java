package com.example.demorabbitmqelastic;
import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
//@ContextConfiguration(initializers = CustomerElasticIT.Initializer.class)
public class CustomerElasticIT {

    @Container
//    public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("elasticsearch:7.6.1")
//            .withExposedPorts(9200, 9300)
//            ;
    public static ElasticsearchContainer elasticsearchContainer = new CustomerElasticsearchContainer();

    @BeforeAll
    static void setup() {
        elasticsearchContainer.start();
    }

    @BeforeEach
    void testIsContainerRunning() {
        assertThat(elasticsearchContainer.isRunning()).isTrue();
        //assertTrue(elasticsearchContainer.isRunning());
        //recreateIndex();
    }


    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

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

    @Autowired
    ElasticsearchTemplate template;


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

    /*
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "elasticsearch.host=" + elasticsearchContainer.getHttpHostAddress(),
                    "elasticsearch.port=" + elasticsearchContainer.getMappedPort(9200)
            ).applyTo(context);
        }
    }
     */

}
