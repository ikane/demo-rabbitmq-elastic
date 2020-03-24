package com.example.demorabbitmqelastic;
import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import com.example.demorabbitmqelastic.repository.CustomerRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
//@ContextConfiguration(initializers = CustomerElasticIT.Initializer.class)
@ContextConfiguration(classes = ElasticsearchConfig.class/*, initializers = CustomerElasticIT.Initializer.class*/)
public class CustomerElasticIT {

    @Container
   /* public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("elasticsearch:7.6.1")
            .withExposedPorts(9200, 9300);*/
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

//    @Autowired
//    RestHighLevelClient elasticsearchClient;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    CustomerRepository customerRepository;


    @Test
    public void testSaveCustomer() {
        // GIVEN
        Customer customer = Customer.builder()
                .id("1")
                .name("Ibrahima KANE")
                .email("irahima.kane@carrefour.com")
                .address(Address.builder().street("1 rue Antoine de Saint Exupery").zipCode("94270").country("FRANCE").build())
                .build();

        // WHEN
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(customer.getId())
                .withObject(customer)
                .build();

        String documentId = elasticsearchOperations.index(indexQuery);

        // THEN
        Page<Customer> customerByName = customerRepository.findCustomerByName("Ibrahima KANE", Pageable.unpaged());

        Optional<Customer> byId = customerRepository.findById("1");

        assertThat(documentId).isNotNull();
        assertThat(customerByName).isNotNull();
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