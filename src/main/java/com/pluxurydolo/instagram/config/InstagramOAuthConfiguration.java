package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.properties.InstagramProperties;
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
    public InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow(InstagramProperties instagramProperties) {
        return new InstagramAuthorizationCodeFlow(instagramProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramExchangeTokenFlow instagramExchangeTokenFlow(
        InstagramApiWebClient instagramApiWebClient,
        InstagramProperties instagramProperties
    ) {
        return new InstagramExchangeTokenFlow(instagramApiWebClient, instagramProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramAccessTokenFlow instagramAccessTokenFlow(
        InstagramProperties instagramProperties,
        InstagramApiWebClient instagramApiWebClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new InstagramAccessTokenFlow(instagramProperties, instagramApiWebClient, abstractTokenSaver);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramRefreshTokenFlow instagramRefreshTokenFlow(InstagramAccessTokenFlow instagramAccessTokenFlow) {
        return new InstagramRefreshTokenFlow(instagramAccessTokenFlow);
    }
}
