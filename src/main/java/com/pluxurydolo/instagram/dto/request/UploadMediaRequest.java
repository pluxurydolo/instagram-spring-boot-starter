package com.pluxurydolo.instagram.dto.request;

public record UploadMediaRequest(
    String mediaUrl,
    String caption
) {
}
