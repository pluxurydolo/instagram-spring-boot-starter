package com.pluxurydolo.instagram.step.video;

import com.pluxurydolo.instagram.dto.Tokens;
import com.pluxurydolo.instagram.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramVideoSenderException;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.security.token.AbstractTokensRetriever;
import com.pluxurydolo.instagram.step.InstagramContainerPublisher;
import com.pluxurydolo.instagram.step.InstagramContainerStatusPoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramVideoSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramVideoSender.class);

    private final InstagramVideoContainerCreator instagramVideoContainerCreator;
    private final InstagramContainerStatusPoller instagramContainerStatusPoller;
    private final InstagramContainerPublisher instagramContainerPublisher;
    private final AbstractTokensRetriever abstractTokensRetriever;
    private final InstagramProperties instagramProperties;

    public InstagramVideoSender(
        InstagramVideoContainerCreator instagramVideoContainerCreator,
        InstagramContainerStatusPoller instagramContainerStatusPoller,
        InstagramContainerPublisher instagramContainerPublisher,
        AbstractTokensRetriever abstractTokensRetriever,
        InstagramProperties instagramProperties
    ) {
        this.instagramVideoContainerCreator = instagramVideoContainerCreator;
        this.instagramContainerStatusPoller = instagramContainerStatusPoller;
        this.instagramContainerPublisher = instagramContainerPublisher;
        this.abstractTokensRetriever = abstractTokensRetriever;
        this.instagramProperties = instagramProperties;
    }

    public Mono<String> upload(UploadMediaRequest request) {
        String videoUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = instagramProperties.userId();

        return abstractTokensRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(accessToken -> uploadVideo(videoUrl, caption, userId, accessToken))
            .map(ContainerResponse::id)
            .doOnSuccess(_ -> LOGGER.info("urue [instagram-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> Mono.error(new InstagramVideoSenderException(throwable)));
    }

    private Mono<ContainerResponse> uploadVideo(String videoUrl, String caption, String userId, String accessToken) {
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
