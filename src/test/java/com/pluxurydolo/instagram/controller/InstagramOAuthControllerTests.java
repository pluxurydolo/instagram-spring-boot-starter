package com.pluxurydolo.instagram.controller;

import com.pluxurydolo.instagram.service.InstagramOAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramOAuthControllerTests {
    private static final InstagramOAuthService OAUTH_SERVICE = mock(InstagramOAuthService.class);
    private static final InstagramOAuthController OAUTH_CONTROLLER = new InstagramOAuthController(OAUTH_SERVICE);

    @Mock
    private ServerWebExchange serverWebExchange;

    @Test
    void testLogin() {
        when(OAUTH_SERVICE.login(serverWebExchange))
            .thenReturn(Mono.empty());

        Mono<Void> result = OAUTH_CONTROLLER.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testCallback() {
        when(OAUTH_SERVICE.callback(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = OAUTH_CONTROLLER.callback("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(OAUTH_SERVICE.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = OAUTH_CONTROLLER.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
