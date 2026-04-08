package com.pluxurydolo.instagram.scheduler.handler;

import com.pluxurydolo.instagram.dto.Tokens;
import com.pluxurydolo.instagram.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.instagram.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class InstagramRefreshTokenSchedulerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramRefreshTokenSchedulerHandler.class);

    private final InstagramRefreshTokenFlow instagramRefreshTokenFlow;
    private final AbstractTokenRetriever abstractTokenRetriever;
    private final RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook;

    public InstagramRefreshTokenSchedulerHandler(
        InstagramRefreshTokenFlow instagramRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever,
        RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook
    ) {
        this.instagramRefreshTokenFlow = instagramRefreshTokenFlow;
        this.abstractTokenRetriever = abstractTokenRetriever;
        this.refreshTokenSchedulerHandlerHook = refreshTokenSchedulerHandlerHook;
    }

    public Mono<String> handle(String jobName) {
        LOGGER.info("iezc Стартовала джоба {}", jobName);

        return abstractTokenRetriever.retrieve()
            .map(Tokens::accessToken)
            .flatMap(instagramRefreshTokenFlow::refreshToken)
            .flatMap(_ -> refreshTokenSchedulerHandlerHook.doAfter())
            .doOnSuccess(_ -> LOGGER.info("knhi Джоба {} успешно завершена", jobName))
            .onErrorResume(throwable -> {
                LOGGER.error("aebc Джоба {} успешно завершена", jobName);
                return refreshTokenSchedulerHandlerHook.handleException(throwable, jobName);
            });
    }
}
