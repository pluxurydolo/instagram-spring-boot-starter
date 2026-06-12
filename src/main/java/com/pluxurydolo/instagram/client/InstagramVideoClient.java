package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.PublishMediaRequest;
import com.pluxurydolo.instagram.exception.InstagramVideoPublicationException;
import com.pluxurydolo.instagram.flow.upload.video.InstagramVideoPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramVideoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramVideoClient.class);

    private final InstagramVideoPublisher instagramVideoPublisher;

    public InstagramVideoClient(InstagramVideoPublisher instagramVideoPublisher) {
        this.instagramVideoPublisher = instagramVideoPublisher;
    }

    public Mono<String> publishVideo(PublishMediaRequest request) {
        return instagramVideoPublisher.publish(request)
            .doOnSuccess(_ -> LOGGER.info("urue [instagram-starter] Видео успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.info("njkw [instagram-starter] Произошла ошибка при публикации видео");
                return Mono.error(new InstagramVideoPublicationException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
