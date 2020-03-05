package br.com.aqueteron.transaction.service;

import org.springframework.stereotype.Component;

@Component
public class TransactionServiceContext {

    private String correlationId;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
