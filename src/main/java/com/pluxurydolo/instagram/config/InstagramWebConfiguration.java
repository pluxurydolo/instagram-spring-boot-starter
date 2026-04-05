package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.controller.InstagramOAuthController;
import com.pluxurydolo.instagram.security.flow.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.security.flow.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.security.flow.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.security.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.service.InstagramOAuthService;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramWebConfiguration {

    @Bean
    public InstagramOAuthController instagramOAuthController(InstagramOAuthService instagramOAuthService) {
        return new InstagramOAuthController(instagramOAuthService);
    }

    @Bean
    public InstagramOAuthService instagramOAuthService(
        InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow,
        InstagramExchangeTokenFlow instagramExchangeTokenFlow,
        InstagramAccessTokenFlow instagramAccessTokenFlow,
        AbstractTokenSaver abstractTokenSaver
    ) {
        return new InstagramOAuthService(
            instagramAuthorizationCodeFlow,
            instagramExchangeTokenFlow,
            instagramAccessTokenFlow,
            abstractTokenSaver
        );
    }

    @Bean
    public InstagramApiWebClient instagramApiWebClient() {
        return new InstagramApiWebClient();
    }

    @Bean
    public InstagramUploadWebClient instagramUploadWebClient() {
        return new InstagramUploadWebClient();
    }
}
