package com.pluxurydolo.instagram.step;

import com.pluxurydolo.instagram.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import reactor.core.publisher.Mono;

public class InstagramContainerPublisher {
    private final InstagramUploadWebClient instagramUploadWebClient;

    public InstagramContainerPublisher(InstagramUploadWebClient instagramUploadWebClient) {
        this.instagramUploadWebClient = instagramUploadWebClient;
    }

    public Mono<ContainerResponse> publish(PublishContainerRequest request) {
        return instagramUploadWebClient.publishContainer(request);
    }
}
