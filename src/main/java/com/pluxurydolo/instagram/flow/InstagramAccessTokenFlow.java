package com.pluxurydolo.instagram.flow;

import com.pluxurydolo.instagram.dto.request.token.AccessTokenRequest;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import reactor.core.publisher.Mono;

public class InstagramAccessTokenFlow {
    private final InstagramAuthProperties instagramAuthProperties;
    private final InstagramApiWebClient instagramApiWebClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public InstagramAccessTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiWebClient instagramApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.instagramAuthProperties = instagramAuthProperties;
        this.instagramApiWebClient = instagramApiWebClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        AccessTokenRequest request = new AccessTokenRequest(appId, appSecret, exchangeToken);

        return instagramApiWebClient.getAccessToken(request)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, exchangeToken));
    }
}
