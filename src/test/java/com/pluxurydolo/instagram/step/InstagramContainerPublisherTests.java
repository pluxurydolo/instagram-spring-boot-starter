package com.pluxurydolo.instagram.step;

import com.pluxurydolo.instagram.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramContainerPublisherTests {

    @Mock
    private InstagramUploadWebClient instagramUploadWebClient;

    @InjectMocks
    private InstagramContainerPublisher instagramContainerPublisher;

    @Test
    void testPublish() {
        when(instagramUploadWebClient.publishContainer(any()))
            .thenReturn(Mono.just(containerResponse()));

        Mono<ContainerResponse> result = instagramContainerPublisher.publish(publishContainerRequest());

        create(result)
            .expectNext(containerResponse())
            .verifyComplete();
    }

    @Test
    void testPublishWhenExceptionOccurred() {
        when(instagramUploadWebClient.publishContainer(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<ContainerResponse> result = instagramContainerPublisher.publish(publishContainerRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static PublishContainerRequest publishContainerRequest() {
        return new PublishContainerRequest("containerId", "userId", "accessToken");
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }
}
