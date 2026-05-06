package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.exception.InstagramAccessTokenFlowException;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramAccessTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramAccessTokenFlow.class);

    private final InstagramAuthProperties instagramAuthProperties;
    private final InstagramApiHttpClient instagramApiHttpClient;
    private final AbstractTokenSaver abstractTokenSaver;

    public InstagramAccessTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenSaver abstractTokenSaver
    ) {
        this.instagramAuthProperties = instagramAuthProperties;
        this.instagramApiHttpClient = instagramApiHttpClient;
        this.abstractTokenSaver = abstractTokenSaver;
    }

    public Mono<String> getToken(String exchangeToken) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        String grantType = "fb_exchange_token";

        return instagramApiHttpClient.getAccessToken(grantType, appId, appSecret, exchangeToken)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, exchangeToken))
            .doOnSuccess(_ -> LOGGER.info("nqsx [instagram-starter] Access token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("difh [instagram-starter] Произошла ошибка при получении access token");
                return Mono.error(new InstagramAccessTokenFlowException(throwable));
            });
    }
}
