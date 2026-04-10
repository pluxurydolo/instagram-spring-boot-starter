package com.pluxurydolo.instagram.filter;

import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.properties.InstagramRateLimitProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Order(HIGHEST_PRECEDENCE)
public class InstagramRateLimitFilter implements WebFilter {
    private final AtomicInteger requestCounter;
    private final InstagramProperties instagramProperties;
    private final InstagramRateLimitProperties instagramRateLimitProperties;

    public InstagramRateLimitFilter(
        AtomicInteger requestCounter,
        InstagramProperties instagramProperties,
        InstagramRateLimitProperties instagramRateLimitProperties
    ) {
        this.requestCounter = requestCounter;
        this.instagramProperties = instagramProperties;
        this.instagramRateLimitProperties = instagramRateLimitProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String loginUrl = instagramProperties.loginUrl();
        String redirectUrl = instagramProperties.redirectUrl();
        String refreshUrl = instagramProperties.refreshUrl();

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (!path.equals(loginUrl) && !path.equals(redirectUrl) && !path.equals(refreshUrl)) {
            return chain.filter(exchange);
        }

        int threshold = instagramRateLimitProperties.threshold();

        if (requestCounter.incrementAndGet() <= threshold) {
            return handleRequest(exchange, chain);
        } else {
            return dropRequest(exchange);
        }
    }

    private Mono<Void> handleRequest(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
            .doFinally(_ -> requestCounter.decrementAndGet());
    }

    private Mono<Void> dropRequest(ServerWebExchange exchange) {
        requestCounter.decrementAndGet();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(TOO_MANY_REQUESTS);

        return response.setComplete();
    }
}
