package com.pluxurydolo.instagram.flow.upload.video;

import com.pluxurydolo.instagram.dto.InstagramTokens;
import com.pluxurydolo.instagram.dto.request.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.request.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.request.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.request.PublishMediaRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.flow.upload.InstagramContainerPublisher;
import com.pluxurydolo.instagram.flow.upload.InstagramContainerStatusPoller;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import reactor.core.publisher.Mono;

public class InstagramVideoPublisher {
    private final InstagramVideoContainerCreator instagramVideoContainerCreator;
    private final InstagramContainerStatusPoller instagramContainerStatusPoller;
    private final InstagramContainerPublisher instagramContainerPublisher;
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final InstagramAuthProperties instagramAuthProperties;

    public InstagramVideoPublisher(
        InstagramVideoContainerCreator instagramVideoContainerCreator,
        InstagramContainerStatusPoller instagramContainerStatusPoller,
        InstagramContainerPublisher instagramContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        InstagramAuthProperties instagramAuthProperties
    ) {
        this.instagramVideoContainerCreator = instagramVideoContainerCreator;
        this.instagramContainerStatusPoller = instagramContainerStatusPoller;
        this.instagramContainerPublisher = instagramContainerPublisher;
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.instagramAuthProperties = instagramAuthProperties;
    }

    public Mono<String> publish(PublishMediaRequest request) {
        String videoUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = instagramAuthProperties.userId();

        return abstractTokenRetriever.retrieve()
            .map(InstagramTokens::accessToken)
            .flatMap(accessToken -> publishVideo(videoUrl, caption, userId, accessToken))
            .map(ContainerResponse::id);
    }

    private Mono<ContainerResponse> publishVideo(String videoUrl, String caption, String userId, String accessToken) {
        CreateContainerRequest request = new CreateContainerRequest(videoUrl, caption, userId, accessToken);

        return instagramVideoContainerCreator.create(request)
            .map(ContainerResponse::id)
            .flatMap(containerId -> publishContainer(containerId, userId, accessToken));
    }

    private Mono<ContainerResponse> publishContainer(String containerId, String userId, String accessToken) {
        ContainerStatusRequest request = new ContainerStatusRequest(containerId, accessToken);

        return instagramContainerStatusPoller.poll(request)
            .map(_ -> new PublishContainerRequest(containerId, userId, accessToken))
            .flatMap(instagramContainerPublisher::publish);
    }
}
