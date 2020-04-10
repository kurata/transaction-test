package br.com.aqueteron.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecondResourceSuccessResponseReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondResourceSuccessResponseReceiver.class);

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    public void receiveMessage(final byte[] in) {
        this.receiveMessage(new String(in));
    }

    public void receiveMessage(final String in) {
        LOGGER.debug(String.format("Receive service two success result from resource id %s", in));
    }
}
