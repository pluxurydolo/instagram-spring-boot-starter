package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.controller.InstagramOAuthController;
import com.pluxurydolo.instagram.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.service.InstagramOAuthService;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramWebConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramOAuthController instagramOAuthController(InstagramOAuthService instagramOAuthService) {
        return new InstagramOAuthController(instagramOAuthService);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramOAuthService instagramOAuthService(
        InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow,
        InstagramExchangeTokenFlow instagramExchangeTokenFlow,
        InstagramAccessTokenFlow instagramAccessTokenFlow,
        InstagramRefreshTokenFlow instagramRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever
    ) {
        return new InstagramOAuthService(
            instagramAuthorizationCodeFlow,
            instagramExchangeTokenFlow,
            instagramAccessTokenFlow,
            instagramRefreshTokenFlow,
            abstractTokenRetriever
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramApiWebClient instagramApiWebClient() {
        return new InstagramApiWebClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramUploadWebClient instagramUploadWebClient() {
        return new InstagramUploadWebClient();
    }
}
