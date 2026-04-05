package com.pluxurydolo.instagram.security.token;

import com.pluxurydolo.instagram.dto.response.MetaTokenResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class AbstractTokenSaver {
    public Mono<String> save(MetaTokenResponse metaTokenResponse) {
        String accessToken = metaTokenResponse.accessToken();
        String tokenType = metaTokenResponse.tokenType();
        Long expiresIn = metaTokenResponse.expiresIn();

        Map<String, String> token = Map.of(
            "access_token", accessToken,
            "token_type", tokenType,
            "expires_in", String.valueOf(expiresIn)
        );

        return saveToken(token);
    }

    protected abstract Mono<String> saveToken(Map<String, String> token);
}
