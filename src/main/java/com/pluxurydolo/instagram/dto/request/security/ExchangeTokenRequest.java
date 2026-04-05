package com.pluxurydolo.instagram.dto.request.security;

public record ExchangeTokenRequest(
    String appId,
    String appSecret,
    String redirectUri,
    String code
) {
}
