package com.pluxurydolo.instagram.service;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.security.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.security.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.security.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.security.token.AbstractTokenSaver;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

import static org.springframework.http.HttpStatus.FOUND;

public class InstagramOAuthService {
    private final InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow;
    private final InstagramExchangeTokenFlow instagramExchangeTokenFlow;
    private final InstagramAccessTokenFlow instagramAccessTokenFlow;
    private final AbstractTokenSaver abstractTokenSaver;

    public InstagramOAuthService(
        InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow,
        InstagramExchangeTokenFlow instagramExchangeTokenFlow,
        InstagramAccessTokenFlow instagramAccessTokenFlow,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.instagramAuthorizationCodeFlow = instagramAuthorizationCodeFlow;
        this.instagramExchangeTokenFlow = instagramExchangeTokenFlow;
        this.instagramAccessTokenFlow = instagramAccessTokenFlow;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<ResponseEntity<Void>> login() {
        String authUrl = instagramAuthorizationCodeFlow.getAuthorizationUrl();
        URI uri = URI.create(authUrl);

        ResponseEntity<Void> responseEntity = ResponseEntity.status(FOUND)
            .location(uri)
            .build();

        return Mono.just(responseEntity);
    }

    public Mono<String> callback(String code) {
        return instagramExchangeTokenFlow.getToken(code)
            .map(TokenResponse::accessToken)
            .flatMap(instagramAccessTokenFlow::getToken)
            .flatMap(abstractTokenSaver::save)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
