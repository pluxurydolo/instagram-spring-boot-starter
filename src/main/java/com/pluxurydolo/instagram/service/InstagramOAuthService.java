package com.pluxurydolo.instagram.service;

import com.pluxurydolo.instagram.flow.oauth.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class InstagramOAuthService {
    private final InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow;
    private final InstagramAccessTokenFlow instagramAccessTokenFlow;
    private final InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    public InstagramOAuthService(
        InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow,
        InstagramAccessTokenFlow instagramAccessTokenFlow,
        InstagramRefreshTokenFlow instagramRefreshTokenFlow
    ) {
        this.instagramAuthorizationCodeFlow = instagramAuthorizationCodeFlow;
        this.instagramAccessTokenFlow = instagramAccessTokenFlow;
        this.instagramRefreshTokenFlow = instagramRefreshTokenFlow;
    }

    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        ServerHttpResponse response = instagramAuthorizationCodeFlow.getResponse(serverWebExchange);
        return response.setComplete();
    }

    public Mono<String> redirect(String code) {
        return instagramAccessTokenFlow.getAccessToken(code);
    }

    public Mono<String> refreshToken() {
        return instagramRefreshTokenFlow.refreshToken();
    }
}
