package com.pluxurydolo.instagram.dto.request.upload;

public record CreateContainerRequest(
    String mediaUrl,
    String caption,
    String userId,
    String accessToken
) {
}
