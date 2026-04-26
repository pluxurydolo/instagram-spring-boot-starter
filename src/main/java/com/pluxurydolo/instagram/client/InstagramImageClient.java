package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.exception.InstagramImageUploadException;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramImageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramImageClient.class);

    private final InstagramImageUploader instagramImageUploader;

    public InstagramImageClient(InstagramImageUploader instagramImageUploader) {
        this.instagramImageUploader = instagramImageUploader;
    }

    public Mono<String> uploadImage(UploadMediaRequest request) {
        return instagramImageUploader.upload(request)
            .doOnSuccess(_ -> LOGGER.info("avyk [instagram-starter] Изображение успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.info("ipjh [instagram-starter] Произошла ошибка при публикации изображения");
                return Mono.error(new InstagramImageUploadException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
