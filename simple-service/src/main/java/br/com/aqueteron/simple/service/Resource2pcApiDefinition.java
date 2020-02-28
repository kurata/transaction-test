package br.com.aqueteron.simple.service;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Api(value = "NumberResource")
@RequestMapping("/api/v1/resources/2pc")
public interface Resource2pcApiDefinition {

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
            value = "Release a Resource.",
            produces = "application/json"
    )
    @PatchMapping("/{resourceId}/release")
    @ApiResponses(
            @ApiResponse(code = 200, message = "Successfully release Resource")
    )
    @Transactional
    ResponseEntity<Resource> patchResourceRelease(@ApiParam(value = "Resource id to release") @PathVariable("resourceId") String resourceId);

    @ApiOperation(
            value = "Rollback a Resource.",
            produces = "application/json"
    )
    @PatchMapping("/{resourceId}/rollback")
    @ApiResponses(
            @ApiResponse(code = 200, message = "Successfully rollback Resource")
    )
    @Transactional
    ResponseEntity<Resource> patchResourceRollback(@ApiParam(value = "Resource id to rollback") @PathVariable("resourceId") String resourceId);

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
