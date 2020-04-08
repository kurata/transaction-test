package br.com.aqueteron.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecondResourceErrorResponseReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondResourceErrorResponseReceiver.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public SecondResourceErrorResponseReceiver(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessage(final String in) {
        LOGGER.debug(String.format("Receive service two error result from resource id %s", in));
        this.rabbitTemplate.convertAndSend("serverOne.resourceRequestRollbackQueue", in);
    }
}
