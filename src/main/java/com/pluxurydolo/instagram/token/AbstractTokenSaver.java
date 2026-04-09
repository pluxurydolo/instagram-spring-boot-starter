package com.pluxurydolo.instagram.token;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public abstract class AbstractTokenSaver {
    private final Clock clock;

    protected AbstractTokenSaver(Clock clock) {
        this.clock = clock;
    }

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

    private String updatedAt() {
        return now(clock)
            .format(ofPattern("yyyy-MM-dd HH:mm:ss 'МСК'"));
    }

    protected abstract Mono<String> saveTokens(Map<String, String> tokens);
}
