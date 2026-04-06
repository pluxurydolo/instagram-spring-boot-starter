package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.scheduler.InstagramRefreshTokenScheduler;
import com.pluxurydolo.instagram.scheduler.handler.InstagramRefreshTokenSchedulerHandler;
import com.pluxurydolo.instagram.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.instagram.security.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.security.token.AbstractTokensRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class InstagramSchedulingConfiguration {

    @Bean
    public InstagramRefreshTokenScheduler instagramRefreshTokenScheduler(
        InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler
    ) {
        return new InstagramRefreshTokenScheduler(instagramRefreshTokenSchedulerHandler);
    }

    @Bean
    public InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler(
        InstagramRefreshTokenFlow instagramRefreshTokenFlow,
        AbstractTokensRetriever abstractTokensRetriever,
        RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook
    ) {
        return new InstagramRefreshTokenSchedulerHandler(
            instagramRefreshTokenFlow,
            abstractTokensRetriever,
            refreshTokenSchedulerHandlerHook
        );
    }
}
