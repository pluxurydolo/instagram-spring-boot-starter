package com.pluxurydolo.instagram.step.image;

import com.pluxurydolo.instagram.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import reactor.core.publisher.Mono;

public class InstagramImageContainerCreator {
    private final InstagramUploadWebClient instagramUploadWebClient;

    public InstagramImageContainerCreator(InstagramUploadWebClient instagramUploadWebClient) {
        this.instagramUploadWebClient = instagramUploadWebClient;
    }

    public Mono<ContainerResponse> create(CreateContainerRequest request) {
        return instagramUploadWebClient.createImageContainer(request);
    }
}
