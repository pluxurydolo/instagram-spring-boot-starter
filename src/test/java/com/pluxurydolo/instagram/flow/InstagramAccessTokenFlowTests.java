package com.pluxurydolo.instagram.flow;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramAccessTokenFlowTests {

    @Mock
    private InstagramProperties instagramProperties;

    @Mock
    private InstagramApiWebClient instagramApiWebClient;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @InjectMocks
    private InstagramAccessTokenFlow instagramAccessTokenFlow;

    @Test
    void testGetToken() {
        when(instagramProperties.appId())
            .thenReturn("appId");
        when(instagramProperties.appSecret())
            .thenReturn("appSecret");
        when(instagramApiWebClient.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokenSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramAccessTokenFlow.getToken("exchangeToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testGetTokenWhenExceptionOccurred() {
        when(instagramProperties.appId())
            .thenReturn("appId");
        when(instagramProperties.appSecret())
            .thenReturn("appSecret");
        when(instagramApiWebClient.getAccessToken(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramAccessTokenFlow.getToken("exchangeToken");

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }
}
