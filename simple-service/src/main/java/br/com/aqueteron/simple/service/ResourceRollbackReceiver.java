package br.com.aqueteron.simple.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class ResourceRollbackReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRollbackReceiver.class);

    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceRollbackReceiver(final ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void receiveMessage(final String in) throws IOException {
        LOGGER.debug(String.format("Receive service rollback resource id %s", in));
        ObjectMapper objectMapper = new ObjectMapper();
        ResourceQueueMessage rqm = objectMapper.readValue(in, ResourceQueueMessage.class);
        Optional<Resource> resourceOptional = this.resourceRepository.findById(rqm.getResourceId());
        if (resourceOptional.isPresent()) {
            this.resourceRepository.delete(resourceOptional.get());
        }
    }
}
