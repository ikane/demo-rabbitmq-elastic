package com.example.demorabbitmqelastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.clustername}")
    private String clusterName;

    @Bean
    RestHighLevelClient client() {

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200", "localhost:9201")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }


}
