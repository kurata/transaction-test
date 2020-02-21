package br.com.aqueteron.simple.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "resource")
@ApiModel(description = "Resource")
public class Resource implements Serializable {

    @Id
    @Column(columnDefinition = "char(36)", nullable = false)
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
