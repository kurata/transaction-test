package br.com.aqueteron.simple.service;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class CorrelationInterceptor implements HandlerInterceptor {

    private static final String CORRELATION_ID_HEADER_NAME = "x-correlation-id";

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) throws Exception {
        String correlationId = getCorrelationIdFromHeader(request);
        MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        return true;

    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        MDC.remove(CORRELATION_ID_LOG_VAR_NAME);
    }

    private String getCorrelationIdFromHeader(final HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER_NAME);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }

}
