package com.pluxurydolo.instagram.flow.upload.video;

import com.pluxurydolo.instagram.dto.request.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.InstagramCreateVideoContainerException;
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
class InstagramVideoContainerCreatorTests {

    @Mock
    private InstagramUploadHttpClient instagramUploadHttpClient;

    @InjectMocks
    private InstagramVideoContainerCreator instagramVideoContainerCreator;

    @Test
    void testCreate() {
        when(instagramUploadHttpClient.createVideoContainer(
            anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(Mono.just(containerResponse()));

        Mono<ContainerResponse> result = instagramVideoContainerCreator.create(createContainerRequest());

        create(result)
            .expectNext(containerResponse())
            .verifyComplete();
    }

    @Test
    void testCreateWhenExceptionOccurred() {
        when(instagramUploadHttpClient.createVideoContainer(
            anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(Mono.error(new RuntimeException()));

        Mono<ContainerResponse> result = instagramVideoContainerCreator.create(createContainerRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramCreateVideoContainerException.class));
    }

    private static CreateContainerRequest createContainerRequest() {
        return new CreateContainerRequest("mediaUrl", "caption", "userId", "accessToken");
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }
}
