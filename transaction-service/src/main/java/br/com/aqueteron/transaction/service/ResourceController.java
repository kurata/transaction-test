package br.com.aqueteron.transaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ResourceController implements ResourceApiDefinition {

    private static final String RESOURCES_FIRST_HOST = "http://localhost:9080/api/v1/resources";

    private static final String RESOURCE_FIRST_HOST = "http://localhost:9080/api/v1/resources/{id}";

    private static final String RESOURCE_SECOND_HOST = "http://localhost:9070/api/v1/resources";

    private static final String RESOURCES_2PC_FIRST_HOST = "http://localhost:9080/api/v1/resources/2pc";

    private static final String RESOURCES_2PC_SECOND_HOST = "http://localhost:9070/api/v1/resources/2pc";

    private static final String COMMIT_RESOURCES_2PC_FIRST_HOST = "http://localhost:9080/api/v1/resources/2pc/{id}/release";

    private static final String COMMIT_RESOURCES_2PC_SECOND_HOST = "http://localhost:9070/api/v1/resources/2pc/{id}/release";

    private static final String ROLLBACK_RESOURCES_2PC_FIRST_HOST = "http://localhost:9080/api/v1/resources/2pc/{id}/rollback";

    private static final String ROLLBACK_RESOURCES_2PC_SECOND_HOST = "http://localhost:9070/api/v1/resources/2pc/{id}/rollback";

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    private RestTemplate restTemplate;

    private TransactionServiceContext transactionServiceContext;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ResourceController(final RestTemplate restTemplate, final TransactionServiceContext transactionServiceContext, final RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.transactionServiceContext = transactionServiceContext;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ResponseEntity<Resource> postResources(final Resource resource) {
        String resourceId = resource.getId();
        LOGGER.debug(String.format("Creating resource with id: %s", resourceId));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-correlation-id", this.transactionServiceContext.getCorrelationId());
        HttpEntity<Resource> resourceHttpEntity = new HttpEntity(resource, httpHeaders);
        try {
            this.restTemplate.postForEntity(RESOURCES_FIRST_HOST, resourceHttpEntity, Resource.class);
            try {
                this.restTemplate.postForEntity(RESOURCE_SECOND_HOST, resourceHttpEntity, Resource.class);
                return ResponseEntity.status(HttpStatus.CREATED).body(resource);
            } catch (HttpClientErrorException e) {
                this.restTemplate.delete(RESOURCE_FIRST_HOST, resourceId);
            }
        } catch (HttpClientErrorException e) {
//            LOGGER.warn(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Override
    public ResponseEntity<Resource> postResources2pc(final Resource resource) {
        String resourceId = resource.getId();
        LOGGER.debug(String.format("Creating resource with id: %s", resourceId));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-correlation-id", this.transactionServiceContext.getCorrelationId());
        try {
            HttpEntity<Resource> resourceHttpEntity = new HttpEntity(resource, httpHeaders);
            this.restTemplate.postForEntity(RESOURCES_2PC_FIRST_HOST, resourceHttpEntity, Resource.class);
            this.restTemplate.postForEntity(RESOURCES_2PC_SECOND_HOST, resourceHttpEntity, Resource.class);
        } catch (HttpClientErrorException e) {
            HttpEntity<Resource> httpEntity = new HttpEntity(null, httpHeaders);
            this.restTemplate.patchForObject(ROLLBACK_RESOURCES_2PC_FIRST_HOST, httpEntity, Resource.class, resourceId);
            this.restTemplate.patchForObject(ROLLBACK_RESOURCES_2PC_SECOND_HOST, httpEntity, Resource.class, resourceId);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        HttpEntity<Resource> httpEntity = new HttpEntity(null, httpHeaders);
        this.restTemplate.patchForObject(COMMIT_RESOURCES_2PC_FIRST_HOST, httpEntity, Resource.class, resourceId);
        this.restTemplate.patchForObject(COMMIT_RESOURCES_2PC_SECOND_HOST, httpEntity, Resource.class, resourceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @Override
    public ResponseEntity<Resource> postResourcesSaga(final Resource resource) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ResourceQueueMessage rqm = new ResourceQueueMessage(this.transactionServiceContext.getCorrelationId(), resource.getId());
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(rqm)).setHeader("x-correlation-id",this.transactionServiceContext.getCorrelationId()).build();
            this.rabbitTemplate.send("serverOne.resourceRequestQueue", message);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
