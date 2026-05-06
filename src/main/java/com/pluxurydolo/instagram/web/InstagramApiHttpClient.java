package com.pluxurydolo.instagram.web;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange(url = "https://graph.facebook.com")
public interface InstagramApiHttpClient {

    @GetExchange("/v20.0/oauth/access_token")
    Mono<TokenResponse> getExchangeToken(
        @RequestParam("client_id") String appId,
        @RequestParam("client_secret") String appSecret,
        @RequestParam("redirect_uri") String redirectUri,
        @RequestParam("code") String code
    );

    @GetExchange("/v20.0/oauth/access_token")
    Mono<TokenResponse> getAccessToken(
        @RequestParam("grant_type") String grantType,
        @RequestParam("client_id") String appId,
        @RequestParam("client_secret") String appSecret,
        @RequestParam("fb_exchange_token") String exchangeToken
    );
}
