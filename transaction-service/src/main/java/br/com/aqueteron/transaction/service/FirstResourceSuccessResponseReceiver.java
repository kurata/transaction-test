package br.com.aqueteron.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstResourceSuccessResponseReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstResourceSuccessResponseReceiver.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public FirstResourceSuccessResponseReceiver(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessage(final String in) {
        LOGGER.debug(String.format("Receive service one success result from resource id %s", in));
        this.rabbitTemplate.convertAndSend("serverTwo.resourceRequestQueue", in);
    }
}
