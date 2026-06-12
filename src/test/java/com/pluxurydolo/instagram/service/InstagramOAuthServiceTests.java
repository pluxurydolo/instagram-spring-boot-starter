package com.pluxurydolo.instagram.service;

import com.pluxurydolo.instagram.flow.oauth.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramOAuthServiceTests {

    @Mock
    private InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow;

    @Mock
    private InstagramAccessTokenFlow instagramAccessTokenFlow;

    @Mock
    private InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @InjectMocks
    private InstagramOAuthService instagramOAuthService;

    @Test
    void testLogin() {
        when(instagramAuthorizationCodeFlow.getResponse(any()))
            .thenReturn(serverHttpResponse);
        when(serverHttpResponse.setComplete())
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramOAuthService.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testRedirect() {
        when(instagramAccessTokenFlow.getAccessToken(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramOAuthService.redirect("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(instagramRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramOAuthService.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
