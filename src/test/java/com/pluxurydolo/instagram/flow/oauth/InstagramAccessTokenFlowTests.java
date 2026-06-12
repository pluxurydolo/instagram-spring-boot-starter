package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.flow.oauth.hook.AccessTokenFlowHook;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.junit.jupiter.api.BeforeEach;
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
    private InstagramAuthProperties instagramAuthProperties;

    @Mock
    private InstagramApiHttpClient instagramApiHttpClient;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @Mock
    private AccessTokenFlowHook accessTokenFlowHook;

    @InjectMocks
    private InstagramAccessTokenFlow instagramAccessTokenFlow;

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
    void testGetAccessToken() {
        when(instagramApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(instagramApiHttpClient.getAccessToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokenSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));
        when(accessTokenFlowHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramAccessTokenFlow.getAccessToken("exchangeToken");

        create(result)
            .expectNext("SUCCESS")
            .verifyComplete();
    }

    @Test
    void testGetAccessTokenWhenExceptionOccurred() {
        when(instagramApiHttpClient.getExchangeToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));
        when(accessTokenFlowHook.handleException(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramAccessTokenFlow.getAccessToken("exchangeToken");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }
}
