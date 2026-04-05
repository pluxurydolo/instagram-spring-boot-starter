package com.pluxurydolo.instagram.security.flow;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import reactor.core.publisher.Mono;

public class InstagramRefreshTokenFlow {
    private final InstagramAccessTokenFlow instagramAccessTokenFlow;

    public InstagramRefreshTokenFlow(InstagramAccessTokenFlow instagramAccessTokenFlow) {
        this.instagramAccessTokenFlow = instagramAccessTokenFlow;
    }

    public Mono<TokenResponse> refreshToken(String currentToken) {
        return instagramAccessTokenFlow.getToken(currentToken);
    }
}
