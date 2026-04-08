package com.pluxurydolo.instagram.token;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public abstract class AbstractTokenSaver {
    public Mono<String> save(TokenResponse tokenResponse, String exchangeToken) {
        String accessToken = tokenResponse.accessToken();
        String tokenType = tokenResponse.tokenType();
        Long expiresIn = tokenResponse.expiresIn();
        String updatedAt = updatedAt();

        Map<String, String> tokens = Map.of(
            "exchange_token", exchangeToken,
            "access_token", accessToken,
            "token_type", tokenType,
            "expires_in", String.valueOf(expiresIn),
            "updated_at", updatedAt
        );

        return saveTokens(tokens);
    }

    private static String updatedAt() {
        LocalDateTime now = now(ZoneId.of("Europe/Moscow"));
        return now.format(ofPattern("yyyy-MM-dd HH:mm:ss 'МСК'"));
    }

    protected abstract Mono<String> saveTokens(Map<String, String> tokens);
}
