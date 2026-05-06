package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.exception.InstagramExchangeTokenFlowException;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.junit.jupiter.api.BeforeEach;
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
class InstagramExchangeTokenFlowTests {

    @Mock
    private InstagramApiHttpClient instagramApiHttpClient;

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
        when(instagramApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));

        Mono<TokenResponse> result = instagramExchangeTokenFlow.getToken("code");

        create(result)
            .expectNext(tokenResponse())
            .verifyComplete();
    }

    @Test
    void testGetTokenWhenExceptionOccurred() {
        when(instagramApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<TokenResponse> result = instagramExchangeTokenFlow.getToken("code");

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramExchangeTokenFlowException.class));
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }
}
