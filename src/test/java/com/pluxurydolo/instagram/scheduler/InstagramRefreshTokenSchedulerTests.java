package com.pluxurydolo.instagram.scheduler;

import com.pluxurydolo.instagram.scheduler.handler.InstagramRefreshTokenSchedulerHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstagramRefreshTokenSchedulerTests {

    @Mock
    private InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler;

    @InjectMocks
    private InstagramRefreshTokenScheduler instagramRefreshTokenScheduler;

    @Test
    void testRefreshToken() {
        when(instagramRefreshTokenSchedulerHandler.handle(anyString()))
            .thenReturn(Mono.just(""));

        assertDoesNotThrow(instagramRefreshTokenScheduler::refreshToken);
    }
}
