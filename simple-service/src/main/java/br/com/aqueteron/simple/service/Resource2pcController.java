package br.com.aqueteron.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Resource2pcController implements Resource2pcApiDefinition {

    private ResourceRepository resourceRepository;

    @Autowired
    public Resource2pcController(final ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ResponseEntity<Resource> postResource(final ResourceApiResource resourceRequested) {
        Optional<Resource> resourceOptional = this.resourceRepository.findById(resourceRequested.getId());
        if (!resourceOptional.isPresent()) {
            Resource resource = new Resource(resourceRequested.getId());
            resource.setState(ResourceState.PREPARED);
            return ResponseEntity.status(HttpStatus.CREATED).body(this.resourceRepository.save(resource));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Resource> patchResourceRelease(final String resourceId) {
        Optional<Resource> resourceOptional = this.resourceRepository.findById(resourceId);
        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            resource.setState(ResourceState.COMMITTED);
            return ResponseEntity.status(HttpStatus.OK).body(this.resourceRepository.save(resource));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Resource> patchResourceRollback(final String resourceId) {
        Optional<Resource> resourceOptional = this.resourceRepository.findById(resourceId);
        if (resourceOptional.isPresent()) {
            this.resourceRepository.deleteById(resourceId);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Resource> deleteResource(final String resourceId) {
        try {
            this.resourceRepository.deleteById(resourceId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
