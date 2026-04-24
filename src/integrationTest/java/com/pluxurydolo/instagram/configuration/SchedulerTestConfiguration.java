package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@TestConfiguration
public class SchedulerTestConfiguration {

    @Bean
    public RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHook() {
        return new RefreshTokenSchedulerHandlerHook() {

            @Override
            public Mono<String> doAfter() {
                return Mono.just("");
            }

            @Override
            public Mono<String> handleException(Throwable throwable, String jobName) {
                return Mono.just("");
            }
        };
    }
}
