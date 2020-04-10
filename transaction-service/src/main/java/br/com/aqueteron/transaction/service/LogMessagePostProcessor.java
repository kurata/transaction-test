package br.com.aqueteron.transaction.service;

import org.slf4j.MDC;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogMessagePostProcessor implements MessagePostProcessor {

    private static final String CORRELATION_ID_HEADER_NAME = "x-correlation-id";

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    private TransactionServiceContext transactionServiceContext;

    @Autowired
    public LogMessagePostProcessor(TransactionServiceContext transactionServiceContext) {
        this.transactionServiceContext = transactionServiceContext;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        Object correlationIdObject = message.getMessageProperties().getHeaders().get(CORRELATION_ID_HEADER_NAME);
        if (correlationIdObject != null) {
            String correlationId = correlationIdObject.toString();
            this.transactionServiceContext.setCorrelationId(correlationId);
            MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        }
        return message;
    }
}
