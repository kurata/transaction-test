package br.com.aqueteron.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ResourceController implements ResourceApiDefinition {

    private static final String RESOURCES_FIRST_HOST = "http://localhost:9080/api/v1/resources";

    private static final String RESOURCE_FIRST_HOST = "http://localhost:9080/api/v1/resources/{id}";

    private static final String SECOND_HOST = "http://localhost:9070/api/v1/resources";

    private RestTemplate restTemplate;

    @Autowired
    public ResourceController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Resource> postResources(final Resource resource) {
        String resourceId = resource.getId();
        try {
            this.restTemplate.postForEntity(RESOURCES_FIRST_HOST, resource, Resource.class);
            try {
                this.restTemplate.postForEntity(SECOND_HOST, resource, Resource.class);
                return ResponseEntity.status(HttpStatus.CREATED).body(resource);
            } catch (HttpClientErrorException e) {
                this.restTemplate.delete(RESOURCE_FIRST_HOST, resourceId);
            }
        } catch (HttpClientErrorException e) {
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Override
    public ResponseEntity<Resource> postResources2pc(final Resource resource) {
//        commitRequestPhase();
//        commitPhase();
        return null;
    }
}
