package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
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
        InstagramApiWebClient instagramApiWebClient,
        InstagramAuthProperties instagramAuthProperties
    ) {
        return new InstagramExchangeTokenFlow(instagramApiWebClient, instagramAuthProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramAccessTokenFlow instagramAccessTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiWebClient instagramApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new InstagramAccessTokenFlow(instagramAuthProperties, instagramApiWebClient, abstractTokenSaver);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramRefreshTokenFlow instagramRefreshTokenFlow(InstagramAccessTokenFlow instagramAccessTokenFlow) {
        return new InstagramRefreshTokenFlow(instagramAccessTokenFlow);
    }
}
