package com.pluxurydolo.instagram.flow.upload;

import com.pluxurydolo.instagram.dto.request.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.exception.InstagramContainerStatusException;
import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        when(instagramPollingProperties.delay())
            .thenReturn(Duration.ofMillis(100));
        when(instagramPollingProperties.maxRepeat())
            .thenReturn(5);
    }

    @Test
    void testPoll() {
        when(instagramUploadHttpClient.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerStatusResponse("FINISHED")));

        Mono<String> result = instagramContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .expectNext("FINISHED")
            .verifyComplete();
    }

    @Test
    void testPollWhenStatusIsProcessing() {
        when(instagramUploadHttpClient.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerStatusResponse("PROCESSING")));

        Mono<String> result = instagramContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(IllegalStateException.class));
    }

    @Test
    void testPollWhenExceptionOccurred() {
        when(instagramUploadHttpClient.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramContainerStatusException.class));
    }

    private static ContainerStatusRequest createContainerStatusRequest() {
        return new ContainerStatusRequest("containerId", "accessToken");
    }

    private static ContainerStatusResponse containerStatusResponse(String status) {
        return new ContainerStatusResponse("id", status, "status");
    }
}
