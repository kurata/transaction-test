package br.com.aqueteron.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController implements ResourceApiDefinition {

    private ResourceRepository resourceRepository;

    @Autowired
    public ResourceController(final ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ResponseEntity<Resource> postNumbers(final Resource resource) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.resourceRepository.save(resource));
    }
}
