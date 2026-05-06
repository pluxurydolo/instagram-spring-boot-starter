package com.pluxurydolo.instagram.flow.upload;

import com.pluxurydolo.instagram.dto.request.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.exception.InstagramImageContainerStatusException;
import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramContainerStatusPollerTests {

    @Mock
    private InstagramUploadHttpClient instagramUploadHttpClient;

    @Mock
    private InstagramPollingProperties instagramPollingProperties;

    @InjectMocks
    private InstagramContainerStatusPoller instagramContainerStatusPoller;

    @Test
    void testPoll() {
        when(instagramPollingProperties.delay())
            .thenReturn(Duration.ofSeconds(1));
        when(instagramPollingProperties.maxRepeat())
            .thenReturn(100);
        when(instagramUploadHttpClient.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerStatusResponse()));

        Mono<String> result = instagramContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .expectNext("FINISHED")
            .verifyComplete();
    }

    @Test
    void testPollWhenExceptionOccurred() {
        when(instagramPollingProperties.delay())
            .thenReturn(Duration.ofSeconds(1));
        when(instagramPollingProperties.maxRepeat())
            .thenReturn(100);
        when(instagramUploadHttpClient.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramImageContainerStatusException.class));
    }

    private static ContainerStatusRequest createContainerStatusRequest() {
        return new ContainerStatusRequest("containerId", "accessToken");
    }

    private static ContainerStatusResponse containerStatusResponse() {
        return new ContainerStatusResponse("id", "FINISHED", "status");
    }
}
