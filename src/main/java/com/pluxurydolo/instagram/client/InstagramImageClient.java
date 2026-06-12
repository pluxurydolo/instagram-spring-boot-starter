package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.PublishMediaRequest;
import com.pluxurydolo.instagram.exception.InstagramImagePublicationException;
import com.pluxurydolo.instagram.flow.upload.image.InstagramImagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramImageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramImageClient.class);

    private final InstagramImagePublisher instagramImagePublisher;

    public InstagramImageClient(InstagramImagePublisher instagramImagePublisher) {
        this.instagramImagePublisher = instagramImagePublisher;
    }

    public Mono<String> publishImage(PublishMediaRequest request) {
        return instagramImagePublisher.publish(request)
            .doOnSuccess(_ -> LOGGER.info("avyk [instagram-starter] Изображение успешно опубликовано"))
            .onErrorResume(throwable -> {
                LOGGER.info("ipjh [instagram-starter] Произошла ошибка при публикации изображения");
                return Mono.error(new InstagramImagePublicationException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
