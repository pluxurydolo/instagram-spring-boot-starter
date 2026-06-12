package com.pluxurydolo.instagram.flow.upload;

import com.pluxurydolo.instagram.dto.request.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramPublishContainerException;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramContainerPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramContainerPublisher.class);

    private final InstagramUploadHttpClient instagramUploadHttpClient;

    public InstagramContainerPublisher(InstagramUploadHttpClient instagramUploadHttpClient) {
        this.instagramUploadHttpClient = instagramUploadHttpClient;
    }

    public Mono<ContainerResponse> publish(PublishContainerRequest request) {
        String containerId = request.containerId();
        String userId = request.userId();
        String accessToken = request.accessToken();

        return instagramUploadHttpClient.publishContainer(userId, containerId, accessToken)
            .doOnSuccess(_ -> LOGGER.info("ynup [instagram-starter] Контейнер {} успешно опубликован", containerId))
            .onErrorResume(throwable -> {
                LOGGER.error("ervb [instagram-starter] Произошла ошибка при публикации контейнера {}", containerId);
                return Mono.error(new InstagramPublishContainerException(throwable));
            });
    }
}
