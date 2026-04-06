package com.pluxurydolo.instagram.step.image;

import com.pluxurydolo.instagram.dto.Tokens;
import com.pluxurydolo.instagram.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramImageSenderException;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.security.token.AbstractTokensRetriever;
import com.pluxurydolo.instagram.step.InstagramContainerPublisher;
import com.pluxurydolo.instagram.step.InstagramContainerStatusPoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramImageSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramImageSender.class);

    private final InstagramImageContainerCreator instagramImageContainerCreator;
    private final InstagramContainerStatusPoller instagramContainerStatusPoller;
    private final InstagramContainerPublisher instagramContainerPublisher;
    private final AbstractTokensRetriever abstractTokensRetriever;
    private final InstagramProperties instagramProperties;

    public InstagramImageSender(
        InstagramImageContainerCreator instagramImageContainerCreator,
        InstagramContainerStatusPoller instagramContainerStatusPoller,
        InstagramContainerPublisher instagramContainerPublisher,
        AbstractTokensRetriever abstractTokensRetriever,
        InstagramProperties instagramProperties
    ) {
        this.instagramImageContainerCreator = instagramImageContainerCreator;
        this.instagramContainerStatusPoller = instagramContainerStatusPoller;
        this.instagramContainerPublisher = instagramContainerPublisher;
        this.abstractTokensRetriever = abstractTokensRetriever;
        this.instagramProperties = instagramProperties;
    }

    public Mono<String> upload(UploadMediaRequest request) {
        String imageUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = instagramProperties.userId();

        return abstractTokensRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(accessToken -> uploadImage(imageUrl, caption, userId, accessToken))
            .map(ContainerResponse::id)
            .doOnSuccess(_ -> LOGGER.info("avyk [instagram-starter] Изображение успешно опубликовано"))
            .onErrorResume(throwable -> Mono.error(new InstagramImageSenderException(throwable)));
    }

    private Mono<ContainerResponse> uploadImage(String imageUrl, String caption, String userId, String accessToken) {
        CreateContainerRequest request = new CreateContainerRequest(imageUrl, caption, userId, accessToken);

        return instagramImageContainerCreator.create(request)
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
