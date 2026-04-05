package com.pluxurydolo.instagram.step.video;

import com.pluxurydolo.instagram.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import reactor.core.publisher.Mono;

public class InstagramVideoContainerCreator {
    private final InstagramUploadWebClient instagramUploadWebClient;

    public InstagramVideoContainerCreator(InstagramUploadWebClient instagramUploadWebClient) {
        this.instagramUploadWebClient = instagramUploadWebClient;
    }

    public Mono<ContainerResponse> create(CreateContainerRequest request) {
        return instagramUploadWebClient.createVideoContainer(request);
    }
}
