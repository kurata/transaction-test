package br.com.aqueteron.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ResourceController implements ResourceApiDefinition {

    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceController(final ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ResponseEntity<Resource> postResource(final Resource resource) {
        Optional<Resource> resourceOptional = this.resourceRepository.findById(resource.getId());
        if (!resourceOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.resourceRepository.save(resource));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
