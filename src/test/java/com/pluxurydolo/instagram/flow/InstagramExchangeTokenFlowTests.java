package com.pluxurydolo.instagram.flow;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramExchangeTokenFlowTests {

    @Mock
    private InstagramApiWebClient instagramApiWebClient;

    @Mock
    private InstagramAuthProperties instagramAuthProperties;

    @InjectMocks
    private InstagramExchangeTokenFlow instagramExchangeTokenFlow;

    @BeforeEach
    void setUp() {
        when(instagramAuthProperties.appId())
            .thenReturn("appId");
        when(instagramAuthProperties.appSecret())
            .thenReturn("appSecret");
        when(instagramAuthProperties.redirectUri())
            .thenReturn("redirectUri");
    }

    @Test
    void testGetToken() {
        when(instagramApiWebClient.getExchangeToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        Mono<TokenResponse> result = instagramExchangeTokenFlow.getToken("code");

        create(result)
            .expectNext(tokenResponse())
            .verifyComplete();
    }

    @Test
    void testGetTokenWhenExceptionOccurred() {
        when(instagramApiWebClient.getExchangeToken(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<TokenResponse> result = instagramExchangeTokenFlow.getToken("code");

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }
}
