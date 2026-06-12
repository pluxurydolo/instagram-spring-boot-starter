package com.pluxurydolo.instagram.flow.upload;

import com.pluxurydolo.instagram.dto.request.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramPublishContainerException;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class InstagramContainerPublisherTests {

    @Mock
    private InstagramUploadHttpClient instagramUploadHttpClient;

    @InjectMocks
    private InstagramContainerPublisher instagramContainerPublisher;

    @Test
    void testPublish() {
        when(instagramUploadHttpClient.publishContainer(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerResponse()));

        Mono<ContainerResponse> result = instagramContainerPublisher.publish(publishContainerRequest());

        create(result)
            .expectNext(containerResponse())
            .verifyComplete();
    }

    @Test
    void testPublishWhenExceptionOccurred() {
        when(instagramUploadHttpClient.publishContainer(anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<ContainerResponse> result = instagramContainerPublisher.publish(publishContainerRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramPublishContainerException.class));
    }

    private static PublishContainerRequest publishContainerRequest() {
        return new PublishContainerRequest("containerId", "userId", "accessToken");
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }
}
