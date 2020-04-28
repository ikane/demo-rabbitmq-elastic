package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Address;
import com.example.demorabbitmqelastic.domain.Customer;
import com.example.demorabbitmqelastic.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public Customer createCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> findCustomers() {
        List<Customer> result = new ArrayList<>();
        this.customerRepository.findAll().forEach(customer -> result.add(customer));
        return result;
    }

    public Customer saveCustomer(Customer dto) {
        Customer customer = Customer.builder().id(dto.getId())
                                 .name(dto.getName())
                                 .email(dto.getEmail())
                                 .gender(dto.getGender())
                                 .address(Address.builder()
                                                 .country(dto.getAddress().getCountry())
                                                 .street(dto.getAddress().getStreet())
                                                 .zipCode(dto.getAddress().getZipCode())
                                                 .build())
                                 .build();

        return this.customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        //customerRepository.findById(customer.getId());
        return this.customerRepository.save(customer);
    }

    public Object getCustomerAggregateByGender() {
        //this.customerRepository.
        //this.elasticsearchOperations.
        /*
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("top_genders")
                                                                 .field("gender")
                                                                 .order(Terms.Order.count(false));
        */
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder() //
                                                                       .withQuery(matchAllQuery()) //
                                                                       .withSearchType(SearchType.DEFAULT) //
                                                                       .addAggregation(terms("genders").field(
                                                                               "gender")) //
                                                                       .build();

        ResultsExtractor<?> resultsExtractor = response -> response.getAggregations();
        Object query = this.elasticsearchOperations.query(searchQuery, resultsExtractor);

        /*
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("gender", "M"));
        CountRequest countRequest = new CountRequest();
        countRequest.source(searchSourceBuilder);

        SearchRequest searchRequest = new SearchRequest("users");
        searchRequest.source(searchSourceBuilder);
        */

        MatchQueryBuilder builder1 = new MatchQueryBuilder("gender", "M");
        SearchQuery searchQuery2 = new NativeSearchQueryBuilder()
                .withQuery(builder1)
                //.withFilter(boolFilter().must(termFilter("gender", "M")))
                .withIndices("users")
                .build();

        long count = this.elasticsearchOperations.count(searchQuery2);

        //*************************
        BoolQueryBuilder builder2 = QueryBuilders.boolQuery();
        builder2.must(new MatchQueryBuilder("gender", "F"));
        builder2.must(new MatchQueryBuilder("married", true));
        builder2.mustNot(new ExistsQueryBuilder("address"));
        //builder.must(QueryBuilders.termQuery("gender", "F"));


        NativeSearchQuery searchQuery3 = new NativeSearchQueryBuilder() //
                                                                       .withQuery(builder2) //
                                                                       .withSearchType(SearchType.DEFAULT) //
                                                                       .build();
        Page<Customer> search = this.customerRepository.search(searchQuery3);
        long totalElements = search.getTotalElements();

        log.info("result {}", query);

        return query;
    }

}
