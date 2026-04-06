package com.pluxurydolo.instagram.scheduler.handler;

import com.pluxurydolo.instagram.dto.Tokens;
import com.pluxurydolo.instagram.scheduler.hook.RefreshTokenSchedulerHandlerHook;
import com.pluxurydolo.instagram.security.flow.InstagramRefreshTokenFlow;
import com.pluxurydolo.instagram.security.token.AbstractTokensRetriever;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramRefreshTokenSchedulerHandlerTests {

    @Mock
    private InstagramRefreshTokenFlow instagramRefreshTokenFlow;

    @Mock
    private AbstractTokensRetriever abstractTokensRetriever;

    @Mock
    private RefreshTokenSchedulerHandlerHook refreshTokenSchedulerHandlerHook;

    @InjectMocks
    private InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler;

    @Test
    void testHandle() {
        when(abstractTokensRetriever.retrieve())
            .thenReturn(Mono.just(tokens()));
        when(instagramRefreshTokenFlow.refreshToken(anyString()))
            .thenReturn(Mono.just(""));
        when(refreshTokenSchedulerHandlerHook.doAfter())
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testHandleWhenExceptionOccurred() {
        when(abstractTokensRetriever.retrieve())
            .thenReturn(Mono.error(new RuntimeException()));
        when(refreshTokenSchedulerHandlerHook.handleException(any(), anyString()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramRefreshTokenSchedulerHandler.handle("jobName");

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static Tokens tokens() {
        return new Tokens("exchangeTokens", "accessToken");
    }
}
