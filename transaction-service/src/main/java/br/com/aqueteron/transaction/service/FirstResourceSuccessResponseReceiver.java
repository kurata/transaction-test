package br.com.aqueteron.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstResourceSuccessResponseReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstResourceSuccessResponseReceiver.class);

    private RabbitTemplate rabbitTemplate;

    private TransactionServiceContext transactionServiceContext;

    @Autowired
    public FirstResourceSuccessResponseReceiver(final RabbitTemplate rabbitTemplate,
                                                final TransactionServiceContext transactionServiceContext) {
        this.rabbitTemplate = rabbitTemplate;
        this.transactionServiceContext = transactionServiceContext;
    }

    public void receiveMessage(final byte[] in) {
        this.receiveMessage(new String(in));
    }

    public void receiveMessage(final String in) {
        LOGGER.debug(String.format("Receive service one success result from resource id %s", in));
        Message message = MessageBuilder.withBody(in.getBytes()).setHeader("x-correlation-id", this.transactionServiceContext.getCorrelationId()).build();
        this.rabbitTemplate.send("serverTwo.resourceRequestQueue", message);
    }
}
