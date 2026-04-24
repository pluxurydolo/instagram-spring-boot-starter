package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.scheduler.InstagramRefreshTokenScheduler;
import com.pluxurydolo.instagram.scheduler.handler.InstagramRefreshTokenSchedulerHandler;
import com.pluxurydolo.instagram.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.instagram.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class InstagramSchedulingConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramRefreshTokenScheduler instagramRefreshTokenScheduler(
        InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler
    ) {
        return new InstagramRefreshTokenScheduler(instagramRefreshTokenSchedulerHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler(
        InstagramRefreshTokenFlow instagramRefreshTokenFlow,
        AbstractTokenRetriever abstractTokenRetriever,
        RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook
    ) {
        return new InstagramRefreshTokenSchedulerHandler(
            instagramRefreshTokenFlow,
            abstractTokenRetriever,
            refreshTokenSchedulerHandlerHook
        );
    }
}
