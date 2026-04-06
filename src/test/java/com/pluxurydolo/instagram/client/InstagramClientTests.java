package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
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
class InstagramClientTests {

    @Mock
    private InstagramImageUploader instagramImageUploader;

    @Mock
    private InstagramVideoUploader instagramVideoUploader;

    @InjectMocks
    private InstagramClient instagramClient;

    @Test
    void testUploadImage() {
        when(instagramImageUploader.upload(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramClient.uploadImage(uploadMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testUploadImageWhenExceptionOccurred() {
        when(instagramImageUploader.upload(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramClient.uploadImage(uploadMediaRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    @Test
    void testUploadVideo() {
        when(instagramVideoUploader.upload(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramClient.uploadVideo(uploadMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testUploadVideoWhenExceptionOccurred() {
        when(instagramVideoUploader.upload(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramClient.uploadVideo(uploadMediaRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static UploadMediaRequest uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }
}
