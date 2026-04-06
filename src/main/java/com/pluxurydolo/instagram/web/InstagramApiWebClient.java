package com.pluxurydolo.instagram.web;

import com.pluxurydolo.instagram.dto.request.security.AccessTokenRequest;
import com.pluxurydolo.instagram.dto.request.security.ExchangeTokenRequest;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.exception.InstagramAccessTokenFlowException;
import com.pluxurydolo.instagram.exception.InstagramExchangeTokenFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class InstagramApiWebClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramApiWebClient.class);

    private final WebClient webClient;

    public InstagramApiWebClient() {
        this.webClient = WebClient.builder()
            .baseUrl("https://graph.facebook.com")
            .build();
    }

    public Mono<TokenResponse> getExchangeToken(ExchangeTokenRequest request) {
        String appId = request.appId();
        String appSecret = request.appSecret();
        String redirectUri = request.redirectUri();
        String code = request.code();

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v20.0/oauth/access_token")
                .queryParam("client_id", appId)
                .queryParam("client_secret", appSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build()
            )
            .retrieve()
            .bodyToMono(TokenResponse.class)
            .doOnSuccess(_ -> LOGGER.info("kyvk [instagram-starter] Exchange token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("fjor [instagram-starter] Произошла ошибка при получении exchange token");
                return Mono.error(new InstagramExchangeTokenFlowException(throwable));
            });
    }

    public Mono<TokenResponse> getAccessToken(AccessTokenRequest request) {
        String appId = request.appId();
        String appSecret = request.appSecret();
        String exchangeToken = request.exchangeToken();

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v20.0/oauth/access_token")
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", appId)
                .queryParam("client_secret", appSecret)
                .queryParam("fb_exchange_token", exchangeToken)
                .build()
            )
            .retrieve()
            .bodyToMono(TokenResponse.class)
            .doOnSuccess(_ -> LOGGER.info("nqsx [instagram-starter] Access token успешно получен"))
            .onErrorResume(throwable -> {
                LOGGER.error("difh [instagram-starter] Произошла ошибка при получении access token");
                return Mono.error(new InstagramAccessTokenFlowException(throwable));
            });
    }
}
