package com.pluxurydolo.instagram.step.image;

import com.pluxurydolo.instagram.dto.Token;
import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.exception.ImageSenderException;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.security.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.step.InstagramContainerPublisher;
import com.pluxurydolo.instagram.step.InstagramContainerStatusPoller;
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
class InstagramImageSenderTests {

    @Mock
    private InstagramImageContainerCreator instagramImageContainerCreator;

    @Mock
    private InstagramContainerStatusPoller instagramContainerStatusPoller;

    @Mock
    private InstagramContainerPublisher instagramContainerPublisher;

    @Mock
    private AbstractTokenRetriever abstractTokenRetriever;

    @Mock
    private InstagramProperties instagramProperties;

    @InjectMocks
    private InstagramImageSender instagramImageSender;

    @Test
    void testUpload() {
        when(instagramProperties.userId())
            .thenReturn("userId");
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(token()));
        when(instagramImageContainerCreator.create(any()))
            .thenReturn(Mono.just(containerResponse()));
        when(instagramContainerStatusPoller.poll(any()))
            .thenReturn(Mono.just(""));
        when(instagramContainerPublisher.publish(any()))
            .thenReturn(Mono.just(containerResponse()));

        Mono<String> result = instagramImageSender.upload(uploadMediaRequest());

        create(result)
            .expectNext("id")
            .verifyComplete();
    }

    @Test
    void testUploadWhenExceptionOccurred() {
        when(instagramProperties.userId())
            .thenReturn("userId");
        when(abstractTokenRetriever.retrieve())
            .thenReturn(Mono.just(token()));
        when(instagramImageContainerCreator.create(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramImageSender.upload(uploadMediaRequest());

        create(result)
            .expectError(ImageSenderException.class)
            .verify();
    }

    private static UploadMediaRequest uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }

    private static Token token() {
        return new Token("accessToken");
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }
}
