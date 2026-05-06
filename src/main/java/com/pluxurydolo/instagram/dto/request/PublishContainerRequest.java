package com.pluxurydolo.instagram.dto.request;

public record PublishContainerRequest(
    String containerId,
    String userId,
    String accessToken
) {
}
