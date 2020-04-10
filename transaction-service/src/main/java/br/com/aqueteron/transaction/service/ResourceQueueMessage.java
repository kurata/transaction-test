package br.com.aqueteron.transaction.service;

import java.io.Serializable;

public class ResourceQueueMessage implements Serializable {

    private final String correlationId;

    private final String resourceId;

    public ResourceQueueMessage() {
        this.correlationId = null;
        this.resourceId = null;
    }

    public ResourceQueueMessage(final String correlationId, final String resourceId) {
        this.correlationId = correlationId;
        this.resourceId = resourceId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getResourceId() {
        return resourceId;
    }

}
