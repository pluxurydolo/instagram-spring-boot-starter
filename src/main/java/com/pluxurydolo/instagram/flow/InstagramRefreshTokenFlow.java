package com.pluxurydolo.instagram.flow;

import reactor.core.publisher.Mono;

public class InstagramRefreshTokenFlow {
    private final InstagramAccessTokenFlow instagramAccessTokenFlow;

    public InstagramRefreshTokenFlow(InstagramAccessTokenFlow instagramAccessTokenFlow) {
        this.instagramAccessTokenFlow = instagramAccessTokenFlow;
    }

    public Mono<String> refreshToken(String accessToken) {
        return instagramAccessTokenFlow.getToken(accessToken);
    }
}
