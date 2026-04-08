package com.pluxurydolo.instagram.dto.request.token;

public record ExchangeTokenRequest(
    String appId,
    String appSecret,
    String redirectUri,
    String code
) {
}
