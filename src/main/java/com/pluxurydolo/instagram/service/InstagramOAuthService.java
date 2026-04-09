package com.pluxurydolo.instagram.service;

import com.pluxurydolo.instagram.dto.Tokens;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.FOUND;

public class InstagramOAuthService {
    private final InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow;
    private final InstagramExchangeTokenFlow instagramExchangeTokenFlow;
    private final InstagramAccessTokenFlow instagramAccessTokenFlow;
    private final InstagramRefreshTokenFlow instagramRefreshTokenFlow;
    private final AbstractTokenRetriever abstractTokenRetriever;

    public InstagramOAuthService(
        InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow,
        InstagramExchangeTokenFlow instagramExchangeTokenFlow,
        InstagramAccessTokenFlow instagramAccessTokenFlow,
        InstagramRefreshTokenFlow instagramRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever
    ) {
        this.instagramAuthorizationCodeFlow = instagramAuthorizationCodeFlow;
        this.instagramExchangeTokenFlow = instagramExchangeTokenFlow;
        this.instagramAccessTokenFlow = instagramAccessTokenFlow;
        this.instagramRefreshTokenFlow = instagramRefreshTokenFlow;
        this.abstractTokenRetriever = abstractTokenRetriever;
    }

    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        String authorizationUrl = instagramAuthorizationCodeFlow.getAuthorizationUrl();
        URI uri = create(authorizationUrl);

        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(FOUND);
        response.getHeaders().setLocation(uri);

        return response.setComplete();
    }

    public Mono<String> callback(String code) {
        return instagramExchangeTokenFlow.getToken(code)
            .map(TokenResponse::accessToken)
            .flatMap(instagramAccessTokenFlow::getToken)
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> refreshToken() {
        return abstractTokenRetriever.retrieve()
            .map(Tokens::exchangeToken)
            .flatMap(instagramRefreshTokenFlow::refreshToken)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
