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

    private static final String RESOURCE_SECOND_HOST = "http://localhost:9070/api/v1/resources";

    private static final String RESOURCES_2PC_FIRST_HOST = "http://localhost:9080/api/v1/resources/2pc";

    private static final String RESOURCES_2PC_SECOND_HOST = "http://localhost:9070/api/v1/resources/2pc";

    private static final String COMMIT_RESOURCES_2PC_FIRST_HOST = "http://localhost:9080/api/v1/resources/2pc/{id}/release";

    private static final String COMMIT_RESOURCES_2PC_SECOND_HOST = "http://localhost:9070/api/v1/resources/2pc/{id}/release";

    private static final String ROLLBACK_RESOURCES_2PC_FIRST_HOST = "http://localhost:9080/api/v1/resources/2pc/{id}/rollback";

    private static final String ROLLBACK_RESOURCES_2PC_SECOND_HOST = "http://localhost:9070/api/v1/resources/2pc/{id}/rollback";

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
                this.restTemplate.postForEntity(RESOURCE_SECOND_HOST, resource, Resource.class);
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
        String resourceId = resource.getId();
        try {
            this.restTemplate.postForEntity(RESOURCES_2PC_FIRST_HOST, resource, Resource.class);
            this.restTemplate.postForEntity(RESOURCES_2PC_SECOND_HOST, resource, Resource.class);
        } catch (HttpClientErrorException e) {
            this.restTemplate.patchForObject(ROLLBACK_RESOURCES_2PC_FIRST_HOST, null, Resource.class, resourceId);
            this.restTemplate.patchForObject(ROLLBACK_RESOURCES_2PC_SECOND_HOST, null, Resource.class, resourceId);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        this.restTemplate.patchForObject(COMMIT_RESOURCES_2PC_FIRST_HOST, null, Resource.class, resourceId);
        this.restTemplate.patchForObject(COMMIT_RESOURCES_2PC_SECOND_HOST, null, Resource.class, resourceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }
}
