package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.exception.InstagramRefreshTokenFlowException;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramRefreshTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramRefreshTokenFlow.class);

    private final InstagramAuthProperties instagramAuthProperties;
    private final InstagramApiHttpClient instagramApiHttpClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public InstagramRefreshTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.instagramAuthProperties = instagramAuthProperties;
        this.instagramApiHttpClient = instagramApiHttpClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> refreshToken(String accessToken) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        String grantType = "fb_exchange_token";

        return instagramApiHttpClient.getAccessToken(grantType, appId, appSecret, accessToken)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, accessToken))
            .doOnSuccess(_ -> LOGGER.info("jbus [instagram-starter] Access token успешно обновлен"))
            .onErrorResume(throwable -> {
                LOGGER.error("zodq [instagram-starter] Произошла ошибка при обновлении access token");
                return Mono.error(new InstagramRefreshTokenFlowException(throwable));
            });
    }
}
