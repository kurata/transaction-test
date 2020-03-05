package br.com.aqueteron.transaction.service;

import java.io.Serializable;

public class TransactionServiceContext implements Serializable {

    private String correlationId;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
