package com.pluxurydolo.instagram.filter;

import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.properties.InstagramRateLimitProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramRateLimitFilterTests {

    @Mock
    private AtomicInteger requestCounter;

    @Mock
    private InstagramProperties instagramProperties;

    @Mock
    private InstagramRateLimitProperties instagramRateLimitProperties;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private WebFilterChain webFilterChain;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock
    private URI uri;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @InjectMocks
    private InstagramRateLimitFilter instagramRateLimitFilter;

    @BeforeEach
    void setUp() {
        when(instagramProperties.loginUrl())
            .thenReturn("loginUrl");
        when(instagramProperties.redirectUrl())
            .thenReturn("redirectUrl");
        when(instagramProperties.refreshUrl())
            .thenReturn("refreshUrl");
        when(serverWebExchange.getRequest())
            .thenReturn(serverHttpRequest);
        when(serverHttpRequest.getURI())
            .thenReturn(uri);
    }

    @Test
    void testFilterWithLoginPath() {
        when(uri.getPath())
            .thenReturn("loginUrl");
        when(instagramRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(5);
        when(requestCounter.decrementAndGet())
            .thenReturn(4);
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithLoginPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("loginUrl");
        when(instagramRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(6);
        when(requestCounter.decrementAndGet())
            .thenReturn(5);
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRedirectPath() {
        when(uri.getPath())
            .thenReturn("redirectUrl");
        when(instagramRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(5);
        when(requestCounter.decrementAndGet())
            .thenReturn(4);
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRedirectPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("redirectUrl");
        when(instagramRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(6);
        when(requestCounter.decrementAndGet())
            .thenReturn(5);
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRefreshPath() {
        when(uri.getPath())
            .thenReturn("refreshUrl");
        when(instagramRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(5);
        when(requestCounter.decrementAndGet())
            .thenReturn(4);
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRefreshPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("refreshUrl");
        when(instagramRateLimitProperties.threshold())
            .thenReturn(5);
        when(requestCounter.incrementAndGet())
            .thenReturn(6);
        when(requestCounter.decrementAndGet())
            .thenReturn(5);
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterOtherPath() {
        when(uri.getPath())
            .thenReturn("other");
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRateLimitFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }
}
