package com.pluxurydolo.instagram.security.flow;

import com.pluxurydolo.instagram.dto.request.security.ExchangeTokenRequest;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import reactor.core.publisher.Mono;

public class InstagramExchangeTokenFlow {
    private final InstagramApiWebClient instagramApiWebClient;
    private final InstagramProperties instagramProperties;

    public InstagramExchangeTokenFlow(
        InstagramApiWebClient instagramApiWebClient,
        InstagramProperties instagramProperties
    ) {
        this.instagramApiWebClient = instagramApiWebClient;
        this.instagramProperties = instagramProperties;
    }

    public Mono<TokenResponse> getToken(String code) {
        String appId = instagramProperties.appId();
        String appSecret = instagramProperties.appSecret();
        String redirectUri = instagramProperties.redirectUri();
        ExchangeTokenRequest request = new ExchangeTokenRequest(appId, appSecret, redirectUri, code);

        return instagramApiWebClient.getExchangeToken(request);
    }
}
