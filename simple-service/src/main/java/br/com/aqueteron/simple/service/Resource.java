package br.com.aqueteron.simple.service;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "resource")
public class Resource implements Serializable {

    @Id
    @Column(columnDefinition = "char(36)", nullable = false)
    @ApiModelProperty(notes = "The resource id")
    private final String id;

    @Enumerated(EnumType.STRING)
    private ResourceState state;

    public Resource() {
        this.id = null;
    }

    public Resource(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public ResourceState getState() {
        return state;
    }

    public void setState(ResourceState state) {
        this.state = state;
    }
}
