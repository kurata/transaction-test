package br.com.aqueteron.simple.service;

import org.slf4j.MDC;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class LogMessagePostProcessor implements MessagePostProcessor {

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        Object correlationId = message.getMessageProperties().getHeaders().get("x-correlation-id");
        if (correlationId != null) {
            MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId.toString());
        }
        return message;
    }
}
