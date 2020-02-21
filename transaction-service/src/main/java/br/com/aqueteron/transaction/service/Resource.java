package br.com.aqueteron.transaction.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "Resource")
public class Resource implements Serializable {

    @ApiModelProperty(notes = "The resource id")
    private final String id;

    public Resource() {
        this.id = null;
    }

    public Resource(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
