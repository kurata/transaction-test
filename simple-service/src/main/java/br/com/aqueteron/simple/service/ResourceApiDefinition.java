package br.com.aqueteron.simple.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;

@Api(value = "NumberResource")
@RequestMapping("/api/v1/resource")
public interface ResourceApiDefinition {

    @ApiOperation(
            value = "Create a Number.",
            consumes = "application/json",
            produces = "application/json"
    )
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    ResponseEntity<Resource> postNumbers(@RequestBody final Resource resource);

}
