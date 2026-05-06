package com.pluxurydolo.instagram.flow.upload;

import com.pluxurydolo.instagram.dto.request.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.exception.InstagramImageContainerStatusException;
import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.function.Function;

public class InstagramContainerStatusPoller {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramContainerStatusPoller.class);

    private final InstagramUploadHttpClient instagramUploadHttpClient;
    private final InstagramPollingProperties instagramPollingProperties;

    public InstagramContainerStatusPoller(
        InstagramUploadHttpClient instagramUploadHttpClient,
        InstagramPollingProperties instagramPollingProperties
    ) {
        this.instagramUploadHttpClient = instagramUploadHttpClient;
        this.instagramPollingProperties = instagramPollingProperties;
    }

    public Mono<String> poll(ContainerStatusRequest request) {
        Duration delay = instagramPollingProperties.delay();
        long delaySeconds = delay.getSeconds();
        int maxRepeat = instagramPollingProperties.maxRepeat();

        String containerId = request.containerId();
        String accessToken = request.accessToken();

        Function<Flux<Long>, Publisher<?>> onRepeat = repeat -> repeat
            .doOnNext(repeatNum -> LOGGER.info(
                "xwdn [instagram-starter] Повторная попытка обработки контейнера произойдет через {} секунд ({}/{})",
                delaySeconds, repeatNum + 1, maxRepeat
            ))
            .delayElements(delay, Schedulers.boundedElastic());

        return Mono.defer(() -> validateContainerStatus(containerId, accessToken))
            .repeatWhenEmpty(maxRepeat, onRepeat);
    }

    private Mono<String> validateContainerStatus(String containerId, String accessToken) {
        String fields = "status_code,status";

        return instagramUploadHttpClient.getContainerStatus(containerId, fields, accessToken)
            .map(ContainerStatusResponse::statusCode)
            .doOnNext(status -> LOGGER.info("qsfx [instagram-starter] Статус контейнера: {}", status))
            .onErrorResume(throwable -> {
                LOGGER.error("ykyn [instagram-starter] Произошла ошибка при проверке статуса контейнера {}", containerId);
                return Mono.error(new InstagramImageContainerStatusException(throwable));
            })
            .filter("FINISHED"::equals);
    }
}
