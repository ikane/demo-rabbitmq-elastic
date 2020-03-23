package com.example.demorabbitmqelastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetSocketAddress;

//@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host:localhost}")
    private String host;

    @Value("${elasticsearch.port:9200}")
    private int port;

    @Bean
    RestHighLevelClient client() {

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(new InetSocketAddress(host, port))
                //.connectedTo("localhost:9200", "localhost:9201")

                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        //return RestClients.create(ClientConfiguration.localhost()).rest();

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(new InetSocketAddress(host, port))
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    /*
    public InetSocketAddress getTcpHost() {
        return new InetSocketAddress(getContainerIpAddress(), getMappedPort(ELASTICSEARCH_DEFAULT_TCP_PORT));
    }

/*
    public String getHttpHostAddress() {
      return getContainerIpAddress() + ":" + getMappedPort(ELASTICSEARCH_DEFAULT_PORT);
    }

     */
}
