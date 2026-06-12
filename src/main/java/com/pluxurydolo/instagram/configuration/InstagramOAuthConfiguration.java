package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.flow.oauth.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.hook.AccessTokenFlowHook;
import com.pluxurydolo.instagram.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
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
    public InstagramAccessTokenFlow instagramAccessTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenSaver abstractTokenSaver,
        AccessTokenFlowHook accessTokenFlowHook
    ) {
        return new InstagramAccessTokenFlow(
            instagramAuthProperties,
            instagramApiHttpClient,
            abstractTokenSaver,
            accessTokenFlowHook
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramRefreshTokenFlow instagramRefreshTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenRetriever abstractTokenRetriever,
        AbstractTokenSaver abstractTokenSaver,
        RefreshTokenFlowHook refreshTokenFlowHook
    ) {
        return new InstagramRefreshTokenFlow(
            instagramAuthProperties,
            instagramApiHttpClient,
            abstractTokenRetriever,
            abstractTokenSaver,
            refreshTokenFlowHook
        );
    }
}
