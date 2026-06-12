package com.pluxurydolo.instagram.scheduler.handler;

import com.pluxurydolo.instagram.flow.oauth.InstagramRefreshTokenFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramRefreshTokenSchedulerHandlerTests {

    @Mock
    private InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    @InjectMocks
    private InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler;

    @Test
    void testHandle() {
        when(instagramRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testHandleWhenExceptionOccurred() {
        when(instagramRefreshTokenFlow.refreshToken())
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}
