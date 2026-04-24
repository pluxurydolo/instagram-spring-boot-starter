package com.pluxurydolo.instagram.filter;

import com.pluxurydolo.instagram.properties.InstagramEndpointProperties;
import com.pluxurydolo.instagram.validator.RequestParamValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.pluxurydolo.instagram.validator.ValidationResult.FAILURE;
import static com.pluxurydolo.instagram.validator.ValidationResult.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.util.MultiValueMap.fromMultiValue;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramRequestParamValidationFilterTests {

    @Mock
    private RequestParamValidator requestParamValidator;

    @Mock
    private InstagramEndpointProperties instagramEndpointProperties;

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
    private InstagramRequestParamValidationFilter instagramRequestParamValidationFilter;

    @BeforeEach
    void setUp() {
        when(instagramEndpointProperties.loginUrl())
            .thenReturn("loginUrl");
        when(instagramEndpointProperties.redirectUrl())
            .thenReturn("redirectUrl");
        when(instagramEndpointProperties.refreshTokenUrl())
            .thenReturn("refreshTokenUrl");
        when(serverWebExchange.getRequest())
            .thenReturn(serverHttpRequest);
        when(serverHttpRequest.getURI())
            .thenReturn(uri);
    }

    @Test
    void testFilterWithLoginPath() {
        when(uri.getPath())
            .thenReturn("loginUrl");
        when(serverHttpRequest.getQueryParams())
            .thenReturn(queryParams());
        when(requestParamValidator.validate(anyString()))
            .thenReturn(Mono.just(SUCCESS));
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRequestParamValidationFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithLoginPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("loginUrl");
        when(serverHttpRequest.getQueryParams())
            .thenReturn(queryParams());
        when(requestParamValidator.validate(anyString()))
            .thenReturn(Mono.just(FAILURE));
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRequestParamValidationFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRedirectPath() {
        when(uri.getPath())
            .thenReturn("redirectUrl");
        when(serverHttpRequest.getQueryParams())
            .thenReturn(queryParams());
        when(requestParamValidator.validate(anyString()))
            .thenReturn(Mono.just(SUCCESS));
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRequestParamValidationFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRedirectPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("redirectUrl");
        when(serverHttpRequest.getQueryParams())
            .thenReturn(queryParams());
        when(requestParamValidator.validate(anyString()))
            .thenReturn(Mono.just(FAILURE));
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRequestParamValidationFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRefreshTokenPath() {
        when(uri.getPath())
            .thenReturn("refreshTokenUrl");
        when(serverHttpRequest.getQueryParams())
            .thenReturn(queryParams());
        when(requestParamValidator.validate(anyString()))
            .thenReturn(Mono.just(SUCCESS));
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRequestParamValidationFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterWithRefreshTokenPathWhenValidationFailed() {
        when(uri.getPath())
            .thenReturn("refreshTokenUrl");
        when(serverHttpRequest.getQueryParams())
            .thenReturn(queryParams());
        when(requestParamValidator.validate(anyString()))
            .thenReturn(Mono.just(FAILURE));
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRequestParamValidationFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    @Test
    void testFilterOtherPath() {
        when(uri.getPath())
            .thenReturn("other");
        when(webFilterChain.filter(any()))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramRequestParamValidationFilter.filter(serverWebExchange, webFilterChain);

        create(result)
            .verifyComplete();
    }

    private static MultiValueMap<String, String> queryParams() {
        Map<String, List<String>> params = Map.of("access_token", List.of("accessToken"));
        return fromMultiValue(params);
    }
}
