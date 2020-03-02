package br.com.aqueteron.simple.service;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Api(value = "Resource")
@RequestMapping("/api/v1/resources")
public interface ResourceApiDefinition {

    @ApiOperation(
            value = "Create a Resource.",
            consumes = "application/json",
            produces = "application/json"
    )
    @PostMapping("")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully create Resource"),
            @ApiResponse(code = 400, message = "")
    })
    @Transactional
    ResponseEntity<Resource> postResource(@RequestBody ResourceApiResource resource);

    @ApiOperation(
            value = "Delete a Resource.",
            produces = "application/json"
    )
    @DeleteMapping("/{resourceId}")
    @ApiResponses(
            @ApiResponse(code = 200, message = "Successfully delete Resource")
    )
    @Transactional
    ResponseEntity<Resource> deleteResource(@ApiParam(value = "Resource id to delete") @PathVariable("resourceId") String resourceId);

}
