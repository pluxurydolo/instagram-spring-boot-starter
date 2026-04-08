package com.pluxurydolo.instagram.flow;

import com.pluxurydolo.instagram.properties.InstagramProperties;

import static java.lang.String.format;

public class InstagramAuthorizationCodeFlow {
    private final InstagramProperties instagramProperties;

    public InstagramAuthorizationCodeFlow(InstagramProperties instagramProperties) {
        this.instagramProperties = instagramProperties;
    }

    public String getAuthorizationUrl() {
        String appId = instagramProperties.appId();
        String redirectUri = instagramProperties.redirectUri();
        String scope = "instagram_basic,instagram_content_publish,business_management";
        String responseType = "code";

        return format(
            "https://www.facebook.com/v20.0/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s&response_type=%s",
            appId, redirectUri, scope, responseType
        );
    }
}
