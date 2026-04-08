package com.pluxurydolo.instagram.flow;

import com.pluxurydolo.instagram.dto.request.token.AccessTokenRequest;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import reactor.core.publisher.Mono;

public class InstagramAccessTokenFlow {
    private final InstagramProperties instagramProperties;
    private final InstagramApiWebClient instagramApiWebClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public InstagramAccessTokenFlow(
        InstagramProperties instagramProperties,
        InstagramApiWebClient instagramApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.instagramProperties = instagramProperties;
        this.instagramApiWebClient = instagramApiWebClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appId = instagramProperties.appId();
        String appSecret = instagramProperties.appSecret();
        AccessTokenRequest request = new AccessTokenRequest(appId, appSecret, exchangeToken);

        return instagramApiWebClient.getAccessToken(request)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, exchangeToken));
    }
}
