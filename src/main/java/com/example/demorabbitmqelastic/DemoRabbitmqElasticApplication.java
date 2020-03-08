package com.example.demorabbitmqelastic;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoRabbitmqElasticApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRabbitmqElasticApplication.class, args);
	}

	@Bean
	public Exchange eventExchange() {
		return new TopicExchange("eventExchange");
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
