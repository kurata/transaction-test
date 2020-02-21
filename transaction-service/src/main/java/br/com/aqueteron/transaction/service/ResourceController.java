package br.com.aqueteron.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ResourceController implements ResourceApiDefinition {

    private static final String FIRST_HOST = "";

    private static final String SECOND_HOST = "";

    private RestTemplate restTemplate;

    @Autowired
    public ResourceController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Resource> postNumbers(final Resource resource) {
        return null;
    }
}
