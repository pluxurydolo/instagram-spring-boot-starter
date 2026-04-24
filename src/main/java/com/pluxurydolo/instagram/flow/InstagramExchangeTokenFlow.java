package com.pluxurydolo.instagram.flow;

import com.pluxurydolo.instagram.dto.request.token.ExchangeTokenRequest;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import reactor.core.publisher.Mono;

public class InstagramExchangeTokenFlow {
    private final InstagramApiWebClient instagramApiWebClient;
    private final InstagramAuthProperties instagramAuthProperties;

    public InstagramExchangeTokenFlow(
        InstagramApiWebClient instagramApiWebClient,
        InstagramAuthProperties instagramAuthProperties
    ) {
        this.instagramApiWebClient = instagramApiWebClient;
        this.instagramAuthProperties = instagramAuthProperties;
    }

    public Mono<TokenResponse> getToken(String code) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        String redirectUri = instagramAuthProperties.redirectUri();
        ExchangeTokenRequest request = new ExchangeTokenRequest(appId, appSecret, redirectUri, code);

        return instagramApiWebClient.getExchangeToken(request);
    }
}
