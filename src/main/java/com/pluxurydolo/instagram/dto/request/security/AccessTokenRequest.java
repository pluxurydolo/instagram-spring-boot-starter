package com.pluxurydolo.instagram.dto.request.security;

public record AccessTokenRequest(
    String appId,
    String appSecret,
    String exchangeToken
) {
}
