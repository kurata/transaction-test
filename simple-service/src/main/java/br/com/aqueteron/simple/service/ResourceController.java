package br.com.aqueteron.simple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ResourceController implements ResourceApiDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceController(final ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ResponseEntity<Resource> postResource(final ResourceApiResource resourceRequested) {
        LOGGER.debug(String.format("Creating resource with id: %s", resourceRequested.getId()));
        Optional<Resource> resourceOptional = this.resourceRepository.findById(resourceRequested.getId());
        if (!resourceOptional.isPresent()) {
            Resource resource = new Resource(resourceRequested.getId());
            resource.setState(ResourceState.COMMITTED);
            return ResponseEntity.status(HttpStatus.CREATED).body(this.resourceRepository.save(resource));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Resource> deleteResource(final String resourceId) {
        LOGGER.debug(String.format("Deleting resource with id: %s", resourceId));
        try {
            this.resourceRepository.deleteById(resourceId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
