package com.pluxurydolo.instagram.dto.request.upload;

public record PublishContainerRequest(
    String containerId,
    String userId,
    String accessToken
) {
}
