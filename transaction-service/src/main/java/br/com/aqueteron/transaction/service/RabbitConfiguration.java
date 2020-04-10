package br.com.aqueteron.transaction.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${transaction.firstResourceSuccessQueue}")
    private String firstResourceSuccessQueueName;

    @Value("${transaction.firstResourceErrorQueue}")
    private String firstResourceErrorQueueName;

    @Value("${transaction.secondResourceSuccessQueue}")
    private String secondResourceSuccessQueueName;

    @Value("${transaction.secondResourceErrorQueue}")
    private String secondResourceErrorQueueName;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public MessageListenerAdapter firstResourceSuccessResponseReceiverListener(final FirstResourceSuccessResponseReceiver firstResourceSuccessResponseReceiver) {
        return new MessageListenerAdapter(firstResourceSuccessResponseReceiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer firstResourceSuccessResponseContainer(
            final ConnectionFactory connectionFactory,
            final MessageListenerAdapter firstResourceSuccessResponseReceiverListener,
            final LogMessagePostProcessor logMessagePostProcessor) {
        SimpleMessageListenerContainer firstResourceSuccessResponseContainer = new SimpleMessageListenerContainer();
        firstResourceSuccessResponseContainer.setConnectionFactory(connectionFactory);
        firstResourceSuccessResponseContainer.setQueueNames(this.firstResourceSuccessQueueName);
        firstResourceSuccessResponseContainer.setMessageListener(firstResourceSuccessResponseReceiverListener);
        firstResourceSuccessResponseContainer.setAfterReceivePostProcessors(logMessagePostProcessor);
        return firstResourceSuccessResponseContainer;
    }

    @Bean
    public MessageListenerAdapter firstResourceErrorResponseReceiverListener(final FirstResourceErrorResponseReceiver firstResourceErrorResponseReceiver) {
        return new MessageListenerAdapter(firstResourceErrorResponseReceiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer firstResourceErrorResponseContainer(
            final ConnectionFactory connectionFactory,
            final MessageListenerAdapter firstResourceErrorResponseReceiverListener,
            final LogMessagePostProcessor logMessagePostProcessor) {
        SimpleMessageListenerContainer firstResourceErrorResponseContainer = new SimpleMessageListenerContainer();
        firstResourceErrorResponseContainer.setConnectionFactory(connectionFactory);
        firstResourceErrorResponseContainer.setQueueNames(this.firstResourceErrorQueueName);
        firstResourceErrorResponseContainer.setMessageListener(firstResourceErrorResponseReceiverListener);
        firstResourceErrorResponseContainer.setAfterReceivePostProcessors(logMessagePostProcessor);
        return firstResourceErrorResponseContainer;
    }

    @Bean
    public MessageListenerAdapter secondResourceSuccessResponseReceiverListener(final SecondResourceSuccessResponseReceiver secondResourceSuccessResponseReceiver) {
        return new MessageListenerAdapter(secondResourceSuccessResponseReceiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer secondResourceSuccessResponseContainer(
            final ConnectionFactory connectionFactory,
            final MessageListenerAdapter secondResourceSuccessResponseReceiverListener,
            final LogMessagePostProcessor logMessagePostProcessor) {
        SimpleMessageListenerContainer secondResourceSuccessResponseContainer = new SimpleMessageListenerContainer();
        secondResourceSuccessResponseContainer.setConnectionFactory(connectionFactory);
        secondResourceSuccessResponseContainer.setQueueNames(this.secondResourceSuccessQueueName);
        secondResourceSuccessResponseContainer.setMessageListener(secondResourceSuccessResponseReceiverListener);
        secondResourceSuccessResponseContainer.setAfterReceivePostProcessors(logMessagePostProcessor);
        return secondResourceSuccessResponseContainer;
    }

    @Bean
    public MessageListenerAdapter secondResourceErrorResponseReceiverListener(final SecondResourceErrorResponseReceiver secondResourceErrorResponseReceiver) {
        return new MessageListenerAdapter(secondResourceErrorResponseReceiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer secondResourceErrorResponseContainer(
            final ConnectionFactory connectionFactory,
            final MessageListenerAdapter secondResourceErrorResponseReceiverListener,
            final LogMessagePostProcessor logMessagePostProcessor) {
        SimpleMessageListenerContainer secondResourceErrorResponseContainer = new SimpleMessageListenerContainer();
        secondResourceErrorResponseContainer.setConnectionFactory(connectionFactory);
        secondResourceErrorResponseContainer.setQueueNames(this.secondResourceErrorQueueName);
        secondResourceErrorResponseContainer.setMessageListener(secondResourceErrorResponseReceiverListener);
        secondResourceErrorResponseContainer.setAfterReceivePostProcessors(logMessagePostProcessor);
        return secondResourceErrorResponseContainer;
    }

}
