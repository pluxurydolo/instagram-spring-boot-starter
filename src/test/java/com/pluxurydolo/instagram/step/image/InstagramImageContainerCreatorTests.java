package com.pluxurydolo.instagram.step.image;

import com.pluxurydolo.instagram.dto.request.upload.CreateContainerRequest;
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
class InstagramImageContainerCreatorTests {

    @Mock
    private InstagramUploadWebClient instagramUploadWebClient;

    @InjectMocks
    private InstagramImageContainerCreator instagramImageContainerCreator;

    @Test
    void testCreate() {
        when(instagramUploadWebClient.createImageContainer(any()))
            .thenReturn(Mono.just(containerResponse()));

        Mono<ContainerResponse> result = instagramImageContainerCreator.create(createContainerRequest());

        create(result)
            .expectNext(containerResponse())
            .verifyComplete();
    }

    @Test
    void testCreateWhenExceptionOccurred() {
        when(instagramUploadWebClient.createImageContainer(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<ContainerResponse> result = instagramImageContainerCreator.create(createContainerRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static CreateContainerRequest createContainerRequest() {
        return new CreateContainerRequest("mediaUrl", "caption", "userId", "accessToken");
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }
}
