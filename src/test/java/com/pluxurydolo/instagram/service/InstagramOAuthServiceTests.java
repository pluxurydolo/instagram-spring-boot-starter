package com.pluxurydolo.instagram.service;

import com.pluxurydolo.instagram.dto.Tokens;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramOAuthServiceTests {

    @Mock
    private InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow;

    @Mock
    private InstagramExchangeTokenFlow instagramExchangeTokenFlow;

    @Mock
    private InstagramAccessTokenFlow instagramAccessTokenFlow;

    @Mock
    private InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    @Mock
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Mock
    private HttpHeaders httpHeaders;

    @InjectMocks
    private InstagramOAuthService instagramOAuthService;

    @Test
    void testLogin() {
        doNothing()
            .when(httpHeaders).setLocation(any());
        when(instagramAuthorizationCodeFlow.getAuthorizationUrl())
            .thenReturn("authorizationUrl");
        when(serverWebExchange.getResponse())
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setStatusCode(any()))
            .thenReturn(true);
        when(serverHttpResponse.getHeaders())
            .thenReturn(httpHeaders);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramOAuthService.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testCallback() {
        when(instagramExchangeTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(instagramAccessTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(""));


        Mono<String> result = instagramOAuthService.callback("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(instagramRefreshTokenFlow.refreshToken(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramOAuthService.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }

    private static Tokens tokens() {
        return new Tokens("exchangeToken", "accessToken");
    }
}
