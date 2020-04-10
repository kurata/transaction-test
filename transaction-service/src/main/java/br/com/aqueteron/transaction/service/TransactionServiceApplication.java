package br.com.aqueteron.transaction.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
public class TransactionServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TransactionServiceApplication.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean
//    @RequestScope
    public TransactionServiceContext transactionServiceContext() {
        return new TransactionServiceContext();
    }
}
