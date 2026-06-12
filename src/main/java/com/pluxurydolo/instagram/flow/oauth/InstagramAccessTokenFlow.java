package com.pluxurydolo.instagram.flow.oauth;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.flow.oauth.hook.AccessTokenFlowHook;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramAccessTokenFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramAccessTokenFlow.class);

    private final InstagramAuthProperties instagramAuthProperties;
    private final InstagramApiHttpClient instagramApiHttpClient;
    private final AbstractTokenSaver abstractTokenSaver;
    private final AccessTokenFlowHook accessTokenFlowHook;

    public InstagramAccessTokenFlow(
        InstagramAuthProperties instagramAuthProperties,
        InstagramApiHttpClient instagramApiHttpClient,
        AbstractTokenSaver abstractTokenSaver,
        AccessTokenFlowHook accessTokenFlowHook
    ) {
        this.instagramAuthProperties = instagramAuthProperties;
        this.instagramApiHttpClient = instagramApiHttpClient;
        this.abstractTokenSaver = abstractTokenSaver;
        this.accessTokenFlowHook = accessTokenFlowHook;
    }

    public Mono<String> getAccessToken(String code) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        String redirectUri = instagramAuthProperties.redirectUri();

        return instagramApiHttpClient.getExchangeToken(appId, appSecret, redirectUri, code)
            .flatMap(this::updateTokens)
            .flatMap(_ -> accessTokenFlowHook.doAfter())
            .thenReturn("SUCCESS")
            .doOnSuccess(_ -> LOGGER.info("nqsx [instagram-starter] Access token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("difh [instagram-starter] Произошла ошибка при получении access token");
                return accessTokenFlowHook.handleException(throwable);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<String> updateTokens(TokenResponse tokenResponse) {
        String appId = instagramAuthProperties.appId();
        String appSecret = instagramAuthProperties.appSecret();
        String grantType = "fb_exchange_token";

        String exchangeToken = tokenResponse.accessToken();

        return instagramApiHttpClient.getAccessToken(grantType, appId, appSecret, exchangeToken)
            .flatMap(response -> abstractTokenSaver.save(response, exchangeToken));
    }
}
