package br.com.aqueteron.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfigure implements WebMvcConfigurer {

    private CorrelationInterceptor correlationInterceptor;

    @Autowired
    public WebMvcConfigure(final CorrelationInterceptor correlationInterceptor) {
        this.correlationInterceptor = correlationInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.correlationInterceptor);
    }
}
