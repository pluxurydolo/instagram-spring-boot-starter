package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.dto.InstagramTokens;
import com.pluxurydolo.instagram.flow.oauth.hook.RefreshTokenFlowHook;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramRefreshTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramRefreshTokenFlow.class);

    private final InstagramAuthProperties instagramAuthProperties;
    private final InstagramApiHttpClient instagramApiHttpClient;
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final AbstractTokenSaver abstractTokenSaver;
    private final RefreshTokenFlowHook refreshTokenFlowHook;

    public InstagramRefreshTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenRetriever abstractTokenRetriever,
        AbstractTokenSaver abstractTokenSaver,
        RefreshTokenFlowHook refreshTokenFlowHook
    ) {
        this.instagramAuthProperties = instagramAuthProperties;
        this.instagramApiHttpClient = instagramApiHttpClient;
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.abstractTokenSaver = abstractTokenSaver;
        this.refreshTokenFlowHook = refreshTokenFlowHook;
    }

    public Mono<String> refreshToken() {
        return abstractTokenRetriever.retrieve()
            .flatMap(this::updateTokens)
            .flatMap(_ -> refreshTokenFlowHook.doAfter())
            .thenReturn("SUCCESS")
            .doOnSuccess(_ -> LOGGER.info("jbus [instagram-starter] Access token успешно обновлен"))
            .onErrorResume(throwable -> {
                LOGGER.error("zodq [instagram-starter] Произошла ошибка при обновлении access token");
                return refreshTokenFlowHook.handleException(throwable);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<String> updateTokens(InstagramTokens instagramTokens) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        String grantType = "fb_exchange_token";
        String oldAccessToken = instagramTokens.accessToken();

        return instagramApiHttpClient.getAccessToken(grantType, appId, appSecret, oldAccessToken)
            .flatMap(tokenResponse -> abstractTokenSaver.save(tokenResponse, oldAccessToken));
    }
}
