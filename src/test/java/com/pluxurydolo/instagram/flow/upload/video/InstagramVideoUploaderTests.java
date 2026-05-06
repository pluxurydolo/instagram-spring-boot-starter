package com.pluxurydolo.instagram.flow.upload.video;

import com.pluxurydolo.instagram.dto.InstagramTokens;
import com.pluxurydolo.instagram.dto.request.UploadMediaRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.flow.upload.InstagramContainerPublisher;
import com.pluxurydolo.instagram.flow.upload.InstagramContainerStatusPoller;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import org.junit.jupiter.api.BeforeEach;
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
class InstagramVideoUploaderTests {

    @Mock
    private InstagramVideoContainerCreator instagramVideoContainerCreator;

    @Mock
    private InstagramContainerStatusPoller instagramContainerStatusPoller;

    @Mock
    private InstagramContainerPublisher instagramContainerPublisher;

    @Mock
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private InstagramAuthProperties instagramAuthProperties;

    @InjectMocks
    private InstagramVideoUploader instagramVideoUploader;

    @BeforeEach
    void setUp() {
        when(instagramAuthProperties.userId())
            .thenReturn("userId");
    }

    @Test
    void testUpload() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(instagramTokens()));
        when(instagramVideoContainerCreator.create(any()))
            .thenReturn(Mono.just(containerResponse()));
        when(instagramContainerStatusPoller.poll(any()))
            .thenReturn(Mono.just(""));
        when(instagramContainerPublisher.publish(any()))
            .thenReturn(Mono.just(containerResponse()));

        Mono<String> result = instagramVideoUploader.upload(uploadMediaRequest());

        create(result)
            .expectNext("id")
            .verifyComplete();
    }

    @Test
    void testUploadWhenExceptionOccurred() {
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramVideoUploader.upload(uploadMediaRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }

    private static UploadMediaRequest uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }

    private static InstagramTokens instagramTokens() {
        return new InstagramTokens("exchangeToken", "accessToken");
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }
}
