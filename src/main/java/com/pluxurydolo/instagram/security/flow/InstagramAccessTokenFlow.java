package com.pluxurydolo.instagram.security.flow;

import com.pluxurydolo.instagram.dto.request.security.AccessTokenRequest;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import reactor.core.publisher.Mono;

public class InstagramAccessTokenFlow {
    private final InstagramProperties instagramProperties;
    private final InstagramApiWebClient instagramApiWebClient;

    public InstagramAccessTokenFlow(InstagramProperties instagramProperties, InstagramApiWebClient instagramApiWebClient) {
        this.instagramProperties = instagramProperties;
        this.instagramApiWebClient = instagramApiWebClient;
    }

    public Mono<TokenResponse> getToken(String exchangeToken) {
        String appId = instagramProperties.appId();
        String appSecret = instagramProperties.appSecret();
        AccessTokenRequest request = new AccessTokenRequest(appId, appSecret, exchangeToken);

        return instagramApiWebClient.getAccessToken(request);
    }
}
