package com.pluxurydolo.instagram.scheduler.handler;

import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramRefreshTokenSchedulerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramRefreshTokenSchedulerHandler.class);

    private final InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    public InstagramRefreshTokenSchedulerHandler(InstagramRefreshTokenFlow instagramRefreshTokenFlow) {
        this.instagramRefreshTokenFlow = instagramRefreshTokenFlow;
    }

    public Mono<String> handle(String jobName) {
        LOGGER.info("iezc [instagram-starter] Стартовала джоба {}", jobName);

        return instagramRefreshTokenFlow.refreshToken()
            .doOnSuccess(_ -> LOGGER.info("knhi [instagram-starter] Джоба {} успешно завершена", jobName))
            .doOnError(_ -> LOGGER.error("aebc [instagram-starter] Произошла ошибка при выполнении джобы {}", jobName));
    }
}
