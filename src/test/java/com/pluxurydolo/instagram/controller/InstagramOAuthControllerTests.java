package com.pluxurydolo.instagram.controller;

import com.pluxurydolo.instagram.service.InstagramOAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.FOUND;
import static reactor.test.StepVerifier.create;

class InstagramOAuthControllerTests {
    private static final InstagramOAuthService OAUTH_SERVICE = mock(InstagramOAuthService.class);
    private static final InstagramOAuthController OAUTH_CONTROLLER = new InstagramOAuthController(OAUTH_SERVICE);

    @Test
    void testLogin() {
        when(OAUTH_SERVICE.login())
            .thenReturn(Mono.just(responseEntity()));

        Mono<ResponseEntity<Void>> result = OAUTH_CONTROLLER.login();

        create(result)
            .expectNext(responseEntity())
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

    private static ResponseEntity<Void> responseEntity() {
        URI uri = URI.create("redirectUri");

        return ResponseEntity.status(FOUND)
            .location(uri)
            .build();
    }
}
