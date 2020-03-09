package br.com.aqueteron.simple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResourceRequestReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRequestReceiver.class);

    public void receiveMessage(String in) {
        LOGGER.debug(String.format("Receive service one request from resource id %s", in));
    }
}
