package com.pluxurydolo.instagram.security.token;

import com.pluxurydolo.instagram.dto.Token;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractTokenRetriever {
    public Mono<Token> retrieve() {
        return retrieveToken()
            .map(AbstractTokenRetriever::mapToToken);
    }

    private static Token mapToToken(Map<String, String> token) {
        String accessToken = token.get("access_token");
        return new Token(accessToken);
    }

    protected abstract Mono<Map<String, String>> retrieveToken();
}
