package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.controller.InstagramOAuthController;
import com.pluxurydolo.instagram.flow.oauth.InstagramAccessTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramAuthorizationCodeFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramExchangeTokenFlow;
import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.service.InstagramOAuthService;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.function.Consumer;

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
    public InstagramApiHttpClient instagramApiHttpClient() {
        WebClient webClient = WebClient.builder()
            .baseUrl("https://graph.facebook.com")
            .build();

        WebClientAdapter exchangeAdapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builderFor(exchangeAdapter)
            .build()
            .createClient(InstagramApiHttpClient.class);
    }

    @Bean
    public InstagramUploadHttpClient instagramUploadHttpClient() {
        Consumer<ClientCodecConfigurer> codec = configurer -> configurer
            .defaultCodecs()
            .maxInMemorySize(16 * 1024 * 1024);

        WebClient webClient = WebClient.builder()
            .baseUrl("https://graph.facebook.com")
            .codecs(codec)
            .build();

        WebClientAdapter exchangeAdapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builderFor(exchangeAdapter)
            .build()
            .createClient(InstagramUploadHttpClient.class);
    }
}
