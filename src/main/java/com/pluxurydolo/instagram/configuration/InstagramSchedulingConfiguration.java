package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.scheduler.InstagramRefreshTokenScheduler;
import com.pluxurydolo.instagram.scheduler.handler.InstagramRefreshTokenSchedulerHandler;
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
        InstagramRefreshTokenFlow instagramRefreshTokenFlow
    ) {
        return new InstagramRefreshTokenSchedulerHandler(instagramRefreshTokenFlow);
    }
}
