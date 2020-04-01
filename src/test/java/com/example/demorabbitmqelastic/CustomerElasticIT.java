package com.example.demorabbitmqelastic;
import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import com.example.demorabbitmqelastic.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Testcontainers
@ContextConfiguration(initializers = CustomerElasticIT.Initializer.class)
public class CustomerElasticIT extends AbstractIntegrationTest {
/*
//    @Container
//    public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("elasticsearch:7.6.1");

    @BeforeAll
    static void setup() {
        elasticsearchContainer.start();
    }

    @BeforeEach
    void testIsContainerRunning() {
        assertThat(elasticsearchContainer.isRunning()).isTrue();
        //recreateIndex();
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }
*/

    @Autowired
    private CustomerService customerService;

//    @Autowired
//    RestHighLevelClient elasticsearchClient;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void testSaveCustomerWithService() {
        // GIVEN
        Customer customer = givenCustomer();

        // WHEN
        Customer createdCustomer = this.customerService.createCustomer(customer);

        // THEN
        List<Customer> customers = this.customerService.findCustomers();

        assertThat(createdCustomer).isNotNull();
        assertThat(customers).isNotNull();
        assertThat(customers).isNotEmpty();
    }

    @Test
    public void testSaveCustomerWithElasticApi() {
        // GIVEN
        Customer customer = givenCustomer();

        // WHEN
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(customer.getId())
                .withObject(customer)
                .withIndexName("users")
                .withType("customer")
                .build();

        String documentId = elasticsearchOperations.index(indexQuery);

        // THEN
        GetQuery query = GetQuery.getById(documentId);
        Customer createdCustomer = elasticsearchOperations.queryForObject(query, Customer.class);

        assertThat(documentId).isNotNull();
        assertThat(createdCustomer).isNotNull();
    }

    private Customer givenCustomer() {
        return Customer.builder()
                .id(UUID.randomUUID().toString())
                .name("Ibrahima KANE")
                .email("ibrahima.kane@carrefour.com")
                .address(Address.builder().street("1 rue Antoine de Saint Exupery").zipCode("94270").country("FRANCE").build())
                .build();
    }


}
