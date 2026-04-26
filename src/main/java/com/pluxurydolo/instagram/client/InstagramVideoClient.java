package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.exception.InstagramVideoUploadException;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramVideoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramVideoClient.class);

    private final InstagramVideoUploader instagramVideoUploader;

    public InstagramVideoClient(InstagramVideoUploader instagramVideoUploader) {
        this.instagramVideoUploader = instagramVideoUploader;
    }

    public Mono<String> uploadVideo(UploadMediaRequest request) {
        return instagramVideoUploader.upload(request)
            .doOnSuccess(_ -> LOGGER.info("urue [instagram-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.info("njkw [instagram-starter] Произошла ошибка при публикации видео");
                return Mono.error(new InstagramVideoUploadException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
