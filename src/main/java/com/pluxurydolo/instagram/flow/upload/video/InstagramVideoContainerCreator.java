package com.pluxurydolo.instagram.flow.upload.video;

import com.pluxurydolo.instagram.dto.request.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramCreateImageContainerException;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramVideoContainerCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramVideoContainerCreator.class);

    private final InstagramUploadHttpClient instagramUploadHttpClient;

    public InstagramVideoContainerCreator(InstagramUploadHttpClient instagramUploadHttpClient) {
        this.instagramUploadHttpClient = instagramUploadHttpClient;
    }

    public Mono<ContainerResponse> create(CreateContainerRequest request) {
        String videoUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();
        String mediaType = "REELS";
        String thumbOffset = "0";
        String shareToFeed = "true";

        return instagramUploadHttpClient.createVideoContainer(
                userId,
                mediaType,
                videoUrl,
                accessToken,
                caption,
                thumbOffset,
                shareToFeed
            )
            .doOnSuccess(_ -> LOGGER.info("xznj [instagram-starter] Контейнер видео {} успешно создан", videoUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("plei [instagram-starter] Произошла ошибка при создании контейнера видео {}", videoUrl);
                return Mono.error(new InstagramCreateImageContainerException(throwable));
            });
    }
}
