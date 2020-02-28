package br.com.aqueteron.simple.service;

import org.springframework.http.ResponseEntity;

public class Resource2pcController implements Resource2pcApiDefinition {

    @Override
    public ResponseEntity<Resource> postResource(ResourceApiResource resource) {
        return null;
    }

    @Override
    public ResponseEntity<Resource> patchResourceRelease(String resourceId) {
        return null;
    }

    @Override
    public ResponseEntity<Resource> patchResourceRollback(String resourceId) {
        return null;
    }

    @Override
    public ResponseEntity<Resource> deleteResource(String resourceId) {
        return null;
    }
}
