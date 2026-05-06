package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.flow.oauth.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramOAuthConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow(InstagramAuthProperties instagramAuthProperties) {
        return new InstagramAuthorizationCodeFlow(instagramAuthProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramExchangeTokenFlow instagramExchangeTokenFlow(
        InstagramApiHttpClient instagramApiHttpClient,
        InstagramAuthProperties instagramAuthProperties
    ) {
        return new InstagramExchangeTokenFlow(instagramApiHttpClient, instagramAuthProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramAccessTokenFlow instagramAccessTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new InstagramAccessTokenFlow(instagramAuthProperties, instagramApiHttpClient, abstractTokenSaver);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramRefreshTokenFlow instagramRefreshTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new InstagramRefreshTokenFlow(instagramAuthProperties, instagramApiHttpClient, abstractTokenSaver);
    }
}
