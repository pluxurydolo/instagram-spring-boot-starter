package com.pluxurydolo.instagram.controller;

import com.pluxurydolo.instagram.service.InstagramOAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramOAuthControllerTests {

    @Mock
    private InstagramOAuthService instagramOAuthService;

    @InjectMocks
    private InstagramOAuthController instagramOAuthController;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Test
    void testLogin() {
        when(instagramOAuthService.login(serverWebExchange))
            .thenReturn(Mono.empty());

        Mono<Void> result = instagramOAuthController.login(serverWebExchange);

        create(result)
            .verifyComplete();
    }

    @Test
    void testRedirect() {
        when(instagramOAuthService.redirect(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramOAuthController.redirect("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(instagramOAuthService.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramOAuthController.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}
