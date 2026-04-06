package com.pluxurydolo.instagram.security.token;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractTokensSaver {
    public Mono<String> save(TokenResponse tokenResponse, String exchangeToken) {
        String accessToken = tokenResponse.accessToken();
        String tokenType = tokenResponse.tokenType();
        Long expiresIn = tokenResponse.expiresIn();

        Map<String, String> tokens = Map.of(
            "exchange_token", exchangeToken,
            "access_token", accessToken,
            "token_type", tokenType,
            "expires_in", String.valueOf(expiresIn)
        );

        return saveTokens(tokens);
    }

    protected abstract Mono<String> saveTokens(Map<String, String> tokens);
}
