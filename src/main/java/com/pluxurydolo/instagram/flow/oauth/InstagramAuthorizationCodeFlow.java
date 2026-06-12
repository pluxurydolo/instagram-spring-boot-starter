package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class InstagramAuthorizationCodeFlow {
    private final InstagramAuthProperties instagramAuthProperties;

    public InstagramAuthorizationCodeFlow(InstagramAuthProperties instagramAuthProperties) {
        this.instagramAuthProperties = instagramAuthProperties;
    }

    public URI getAuthorizationUri() {
        String appId = instagramAuthProperties.appId();
        String redirectUri = instagramAuthProperties.redirectUri();

        return UriComponentsBuilder.fromUriString("https://www.facebook.com/v20.0/dialog/oauth")
            .queryParam("client_id", appId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("scope", "instagram_basic,instagram_content_publish,business_management")
            .queryParam("response_type", "code")
            .build()
            .toUri();
    }
}
