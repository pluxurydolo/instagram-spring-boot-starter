package com.pluxurydolo.instagram.step;

import com.pluxurydolo.instagram.dto.request.upload.ContainerStatusRequest;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.properties.PollingProperties;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
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
    private InstagramUploadWebClient instagramUploadWebClient;

    @Mock
    private PollingProperties pollingProperties;

    @InjectMocks
    private InstagramContainerStatusPoller instagramContainerStatusPoller;

    @Test
    void testPoll() {
        when(pollingProperties.delay())
            .thenReturn(Duration.ofSeconds(1));
        when(pollingProperties.maxRepeat())
            .thenReturn(100);
        when(instagramUploadWebClient.getContainerStatus(anyString(), anyString()))
            .thenReturn(Mono.just(containerStatusResponse()));

        Mono<String> result = instagramContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .expectNext("FINISHED")
            .verifyComplete();
    }

    @Test
    void testPollWhenExceptionOccurred() {
        when(pollingProperties.delay())
            .thenReturn(Duration.ofSeconds(1));
        when(pollingProperties.maxRepeat())
            .thenReturn(100);
        when(instagramUploadWebClient.getContainerStatus(anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramContainerStatusPoller.poll(createContainerStatusRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static ContainerStatusRequest createContainerStatusRequest() {
        return new ContainerStatusRequest("containerId", "accessToken");
    }

    private static ContainerStatusResponse containerStatusResponse() {
        return new ContainerStatusResponse("id", "FINISHED", "status");
    }
}
