package com.pluxurydolo.instagram.flow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramRefreshTokenFlowTests {

    @Mock
    private InstagramAccessTokenFlow instagramAccessTokenFlow;

    @InjectMocks
    private InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    @Test
    void testRefreshToken() {
        when(instagramAccessTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramRefreshTokenFlow.refreshToken("currentToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshTokenWhenExceptionOccurred() {
        when(instagramAccessTokenFlow.getToken(anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramRefreshTokenFlow.refreshToken("currentToken");

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }
}
