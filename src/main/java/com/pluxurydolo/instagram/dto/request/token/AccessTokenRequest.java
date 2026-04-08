package com.pluxurydolo.instagram.dto.request.token;

public record AccessTokenRequest(
    String appId,
    String appSecret,
    String exchangeToken
) {
}
