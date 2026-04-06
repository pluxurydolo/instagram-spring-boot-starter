package com.pluxurydolo.instagram.service;

import com.pluxurydolo.instagram.dto.Tokens;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.security.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.security.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.security.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.security.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.security.token.AbstractTokensRetriever;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.FOUND;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramOAuthServiceTests {

    @Mock
    private InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow;

    @Mock
    private InstagramExchangeTokenFlow instagramExchangeTokenFlow;

    @Mock
    private InstagramAccessTokenFlow instagramAccessTokenFlow;

    @Mock
    private InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    @Mock
    private AbstractTokensRetriever abstractTokensRetriever;

    @InjectMocks
    private InstagramOAuthService instagramOAuthService;

    @Test
    void testLogin() {
        when(instagramAuthorizationCodeFlow.getAuthorizationUrl())
            .thenReturn("authorizationUrl");

        Mono<ResponseEntity<Void>> result = instagramOAuthService.login();

        create(result)
            .expectNext(responseEntity())
            .verifyComplete();
    }

    @Test
    void testCallback() {
        when(instagramExchangeTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(instagramAccessTokenFlow.getToken(anyString()))
            .thenReturn(Mono.just(""));


        Mono<String> result = instagramOAuthService.callback("code");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testRefreshToken() {
        when(abstractTokensRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(instagramRefreshTokenFlow.refreshToken(anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramOAuthService.refreshToken();

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static ResponseEntity<Void> responseEntity() {
        URI uri = URI.create("authorizationUrl");

        return ResponseEntity.status(FOUND)
            .location(uri)
            .build();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }

    private static Tokens tokens() {
        return new Tokens("exchangeToken", "accessToken");
    }
}
