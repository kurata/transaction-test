package br.com.aqueteron.simple.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "Resource")
public class ResourceApiResource implements Serializable {

    @ApiModelProperty(notes = "The resource id")
    private final String id;

    public ResourceApiResource() {
        this.id = null;
    }

    public ResourceApiResource(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
