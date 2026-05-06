package com.pluxurydolo.instagram.dto.request;

public record CreateContainerRequest(
    String mediaUrl,
    String caption,
    String userId,
    String accessToken
) {
}
