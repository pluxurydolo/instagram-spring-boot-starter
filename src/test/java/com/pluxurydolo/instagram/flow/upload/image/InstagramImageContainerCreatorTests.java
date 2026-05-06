package com.pluxurydolo.instagram.flow.upload.image;

import com.pluxurydolo.instagram.dto.request.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramCreateImageContainerException;
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
class InstagramImageContainerCreatorTests {

    @Mock
    private InstagramUploadHttpClient instagramUploadHttpClient;

    @InjectMocks
    private InstagramImageContainerCreator instagramImageContainerCreator;

    @Test
    void testCreate() {
        when(instagramUploadHttpClient.createImageContainer(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerResponse()));

        Mono<ContainerResponse> result = instagramImageContainerCreator.create(createContainerRequest());

        create(result)
            .expectNext(containerResponse())
            .verifyComplete();
    }

    @Test
    void testCreateWhenExceptionOccurred() {
        when(instagramUploadHttpClient.createImageContainer(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<ContainerResponse> result = instagramImageContainerCreator.create(createContainerRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramCreateImageContainerException.class));
    }

    private static CreateContainerRequest createContainerRequest() {
        return new CreateContainerRequest("mediaUrl", "caption", "userId", "accessToken");
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }
}
