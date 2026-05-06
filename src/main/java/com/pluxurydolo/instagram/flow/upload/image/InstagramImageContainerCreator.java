package com.pluxurydolo.instagram.flow.upload.image;

import com.pluxurydolo.instagram.dto.request.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramCreateImageContainerException;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramImageContainerCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramImageContainerCreator.class);

    private final InstagramUploadHttpClient instagramUploadHttpClient;

    public InstagramImageContainerCreator(InstagramUploadHttpClient instagramUploadHttpClient) {
        this.instagramUploadHttpClient = instagramUploadHttpClient;
    }

    public Mono<ContainerResponse> create(CreateContainerRequest request) {
        String imageUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();

        return instagramUploadHttpClient.createImageContainer(userId, imageUrl, accessToken, caption)
            .doOnSuccess(_ -> LOGGER.info("erhs [instagram-starter] Контейнер изображения {} успешно создан", imageUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("dhwr [instagram-starter] Произошла ошибка при создании контейнера изображения {}", imageUrl);
                return Mono.error(new InstagramCreateImageContainerException(throwable));
            });
    }
}
