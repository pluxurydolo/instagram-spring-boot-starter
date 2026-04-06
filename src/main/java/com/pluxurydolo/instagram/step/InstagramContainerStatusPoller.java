package com.pluxurydolo.instagram.step;

import com.pluxurydolo.instagram.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.properties.PollingProperties;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
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

    private final InstagramUploadWebClient instagramUploadWebClient;
    private final PollingProperties pollingProperties;

    public InstagramContainerStatusPoller(
        InstagramUploadWebClient instagramUploadWebClient,
        PollingProperties pollingProperties
    ) {
        this.instagramUploadWebClient = instagramUploadWebClient;
        this.pollingProperties = pollingProperties;
    }

    public Mono<String> poll(ContainerStatusRequest request) {
        Duration delay = pollingProperties.delay();
        long delaySeconds = delay.getSeconds();
        int maxRepeat = pollingProperties.maxRepeat();

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
        return instagramUploadWebClient.getContainerStatus(containerId, accessToken)
            .map(ContainerStatusResponse::statusCode)
            .doOnNext(status -> LOGGER.info("qsfx [instagram-starter] Статус контейнера: {}", status))
            .filter("FINISHED"::equals);
    }
}
