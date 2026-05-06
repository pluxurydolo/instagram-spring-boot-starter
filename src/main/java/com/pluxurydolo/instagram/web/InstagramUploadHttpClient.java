package com.pluxurydolo.instagram.web;

import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@HttpExchange(url = "https://graph.facebook.com")
public interface InstagramUploadHttpClient {

    @PostExchange(url = "/v20.0/{userId}/media", contentType = APPLICATION_FORM_URLENCODED_VALUE)
    Mono<ContainerResponse> createImageContainer(
        @PathVariable String userId,
        @RequestParam("image_url") String imageUrl,
        @RequestParam("access_token") String accessToken,
        @RequestParam("caption") String caption
    );

    @PostExchange(url = "/v20.0/{userId}/media", contentType = APPLICATION_FORM_URLENCODED_VALUE)
    Mono<ContainerResponse> createVideoContainer(
        @PathVariable String userId,
        @RequestParam("media_type") String mediaType,
        @RequestParam("video_url") String videoUrl,
        @RequestParam("access_token") String accessToken,
        @RequestParam("caption") String caption,
        @RequestParam("thumb_offset") String thumbOffset,
        @RequestParam("share_to_feed") String shareToFeed
    );

    @PostExchange(url = "/v20.0/{userId}/media_publish", contentType = APPLICATION_FORM_URLENCODED_VALUE)
    Mono<ContainerResponse> publishContainer(
        @PathVariable String userId,
        @RequestParam("creation_id") String containerId,
        @RequestParam("access_token") String accessToken
    );

    @GetExchange(url = "/v20.0/{containerId}")
    Mono<ContainerStatusResponse> getContainerStatus(
        @PathVariable String containerId,
        @RequestParam("fields") String fields,
        @RequestParam("access_token") String accessToken
    );
}
