package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.dto.InstagramTokens;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
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
class InstagramRefreshTokenFlowTests {

    @Mock
    private InstagramAuthProperties instagramAuthProperties;

    @Mock
    private InstagramApiHttpClient instagramApiHttpClient;

    @Mock
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private AbstractTokenSaver abstractTokenSaver;

    @Mock
    private RefreshTokenFlowHook refreshTokenFlowHook;

    @InjectMocks
    private InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    @BeforeEach
    void setUp() {
        when(instagramAuthProperties.appId())
            .thenReturn("appId");
        when(instagramAuthProperties.appSecret())
            .thenReturn("appSecret");
    }

    @Test
    void testRefreshToken() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(instagramTokens()));
        when(instagramApiHttpClient.getAccessToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(abstractTokenSaver.save(any(), anyString()))
            .thenReturn(Mono.just(""));
        when(refreshTokenFlowHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramRefreshTokenFlow.refreshToken();

        create(result)
            .expectNext("SUCCESS")
            .verifyComplete();
    }

    @Test
    void testRefreshTokenWhenExceptionOccurred() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(instagramTokens()));
        when(instagramApiHttpClient.getAccessToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));
        when(refreshTokenFlowHook.handleException(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramRefreshTokenFlow.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static InstagramTokens instagramTokens() {
        return new InstagramTokens("exchangeToken", "accessToken");
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }
}
