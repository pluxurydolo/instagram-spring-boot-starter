package com.pluxurydolo.instagram.security.flow;

import com.pluxurydolo.instagram.dto.request.security.AccessTokenRequest;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.security.token.AbstractTokensSaver;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import reactor.core.publisher.Mono;

public class InstagramAccessTokenFlow {
    private final InstagramProperties instagramProperties;
    private final InstagramApiWebClient instagramApiWebClient;
    private final AbstractTokensSaver abstractTokensSaver;

    public InstagramAccessTokenFlow(
        InstagramProperties instagramProperties,
        InstagramApiWebClient instagramApiWebClient,
        AbstractTokensSaver abstractTokensSaver
    ) {
        this.instagramProperties = instagramProperties;
        this.instagramApiWebClient = instagramApiWebClient;
        this.abstractTokensSaver = abstractTokensSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appId = instagramProperties.appId();
        String appSecret = instagramProperties.appSecret();
        AccessTokenRequest request = new AccessTokenRequest(appId, appSecret, exchangeToken);

        return instagramApiWebClient.getAccessToken(request)
            .flatMap(tokenResponse -> abstractTokensSaver.save(tokenResponse, exchangeToken));
    }
}
