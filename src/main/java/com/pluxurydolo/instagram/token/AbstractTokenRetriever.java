package com.pluxurydolo.instagram.token;

import com.pluxurydolo.instagram.dto.InstagramTokens;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractTokenRetriever {
    public Mono<InstagramTokens> retrieve() {
        return retrieveTokens()
            .map(AbstractTokenRetriever::mapToTokens);
    }

    private static InstagramTokens mapToTokens(Map<String, String> tokens) {
        String exchangeToken = tokens.get("exchange_token");
        String accessToken = tokens.get("access_token");
        return new InstagramTokens(exchangeToken, accessToken);
    }

    protected abstract Mono<Map<String, String>> retrieveTokens();
}
