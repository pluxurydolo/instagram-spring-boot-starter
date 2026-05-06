package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.exception.InstagramExchangeTokenFlowException;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramExchangeTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramExchangeTokenFlow.class);

    private final InstagramApiHttpClient instagramApiHttpClient;
    private final InstagramAuthProperties instagramAuthProperties;

    public InstagramExchangeTokenFlow(
        InstagramApiHttpClient instagramApiHttpClient,
        InstagramAuthProperties instagramAuthProperties
    ) {
        this.instagramApiHttpClient = instagramApiHttpClient;
        this.instagramAuthProperties = instagramAuthProperties;
    }

    public Mono<TokenResponse> getToken(String code) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        String redirectUri = instagramAuthProperties.redirectUri();

        return instagramApiHttpClient.getExchangeToken(appId, appSecret, redirectUri, code)
            .doOnSuccess(_ -> LOGGER.info("kyvk [instagram-starter] Exchange token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("fjor [instagram-starter] Произошла ошибка при получении exchange token");
                return Mono.error(new InstagramExchangeTokenFlowException(throwable));
            });
    }
}
