package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.security.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.security.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.security.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.security.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.security.token.AbstractTokensSaver;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramOAuthConfiguration {

    @Bean
    public InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow(InstagramProperties instagramProperties) {
        return new InstagramAuthorizationCodeFlow(instagramProperties);
    }

    @Bean
    public InstagramExchangeTokenFlow instagramExchangeTokenFlow(
        InstagramApiWebClient instagramApiWebClient,
        InstagramProperties instagramProperties
    ) {
        return new InstagramExchangeTokenFlow(instagramApiWebClient, instagramProperties);
    }

    @Bean
    public InstagramAccessTokenFlow instagramAccessTokenFlow(
        InstagramProperties instagramProperties,
        InstagramApiWebClient instagramApiWebClient,
        AbstractTokensSaver abstractTokensSaver
    ) {
        return new InstagramAccessTokenFlow(instagramProperties, instagramApiWebClient, abstractTokensSaver);
    }

    @Bean
    public InstagramRefreshTokenFlow instagramRefreshTokenFlow(InstagramAccessTokenFlow instagramAccessTokenFlow) {
        return new InstagramRefreshTokenFlow(instagramAccessTokenFlow);
    }
}
