package br.com.aqueteron.simple.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${transaction.resourceRequestQueue}")
    private String resourceRequestQueue;

    @Value("${transaction.resourceRollbackQueue}")
    private String resourceRollbackQueue;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public MessageListenerAdapter resourceRequestReceiverListener(final ResourceRequestReceiver resourceRequestReceiver) {
        return new MessageListenerAdapter(resourceRequestReceiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer resourceRequestReceiverContainer(final ConnectionFactory connectionFactory, final MessageListenerAdapter resourceRequestReceiverListener) {
        SimpleMessageListenerContainer resourceRequestReceiverContainer = new SimpleMessageListenerContainer();
        resourceRequestReceiverContainer.setConnectionFactory(connectionFactory);
        resourceRequestReceiverContainer.setQueueNames(this.resourceRequestQueue);
        resourceRequestReceiverContainer.setMessageListener(resourceRequestReceiverListener);
        resourceRequestReceiverContainer.setAfterReceivePostProcessors(new LogMessagePostProcessor());
        return resourceRequestReceiverContainer;
    }

    @Bean
    public MessageListenerAdapter resourceRollbackReceiverListener(final ResourceRollbackReceiver resourceRollbackReceiver) {
        return new MessageListenerAdapter(resourceRollbackReceiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer resourceRollbackReceiverContainer(final ConnectionFactory connectionFactory, final MessageListenerAdapter resourceRequestReceiverListener) {
        SimpleMessageListenerContainer resourceRequestReceiverContainer = new SimpleMessageListenerContainer();
        resourceRequestReceiverContainer.setConnectionFactory(connectionFactory);
        resourceRequestReceiverContainer.setQueueNames(this.resourceRollbackQueue);
        resourceRequestReceiverContainer.setMessageListener(resourceRequestReceiverListener);
        resourceRequestReceiverContainer.setAfterReceivePostProcessors(new LogMessagePostProcessor());
        return resourceRequestReceiverContainer;
    }
}
