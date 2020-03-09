package br.com.aqueteron.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecondResourceSuccessResponseReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondResourceSuccessResponseReceiver.class);

    public void receiveMessage(String in) {
        LOGGER.debug(String.format("Receive service one success result from resource id %s", in));
    }
}
