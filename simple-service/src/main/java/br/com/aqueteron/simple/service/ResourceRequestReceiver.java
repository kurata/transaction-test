package br.com.aqueteron.simple.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class ResourceRequestReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRequestReceiver.class);

    @Value("${transaction.resourceResponseSuccessQueue}")
    private String resourceResponseSuccessQueue;

    @Value("${transaction.resourceResponseErrorQueue}")
    private String resourceResponseErrorQueue;

    private ResourceRepository resourceRepository;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ResourceRequestReceiver(final ResourceRepository resourceRepository, final RabbitTemplate rabbitTemplate) {
        this.resourceRepository = resourceRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessage(final String in) throws IOException {
        LOGGER.debug(String.format("Receive service request from resource id %s", in));
        ObjectMapper objectMapper = new ObjectMapper();
        ResourceQueueMessage rqm = objectMapper.readValue(in, ResourceQueueMessage.class);
        Optional<Resource> resourceOptional = this.resourceRepository.findById(rqm.getResourceId());
        if (!resourceOptional.isPresent()) {
            Resource resource = new Resource(rqm.getResourceId());
            resource.setState(ResourceState.COMMITTED);
            this.resourceRepository.save(resource);
            this.rabbitTemplate.convertAndSend(this.resourceResponseSuccessQueue, objectMapper.writeValueAsString(rqm));
        } else {
            this.rabbitTemplate.convertAndSend(this.resourceResponseErrorQueue, objectMapper.writeValueAsString(rqm));
        }
    }
}
