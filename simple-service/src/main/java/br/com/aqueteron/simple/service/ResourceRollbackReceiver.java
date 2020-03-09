package br.com.aqueteron.simple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

@Component
public class ResourceRollbackReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRollbackReceiver.class);

    public void receiveMessage(String in) {
        LOGGER.debug(String.format("Receive service one rollback resource id %s", in));
    }
}
