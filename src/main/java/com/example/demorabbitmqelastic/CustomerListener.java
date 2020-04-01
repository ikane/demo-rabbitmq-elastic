package com.example.demorabbitmqelastic;

import com.example.demorabbitmqelastic.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CustomerListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerListener.class);

    @RabbitListener(queues = {"customer.created"})
    public void receive(@Payload Customer customer) {
        //String payload = new String(message.getBody());
        LOGGER.info("Message received: {} ...", customer);
    }
}
