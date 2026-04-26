package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
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
class InstagramVideoClientTests {

    @Mock
    private InstagramVideoUploader instagramVideoUploader;

    @InjectMocks
    private InstagramVideoClient instagramVideoClient;

    @Test
    void testUploadVideo() {
        when(instagramVideoUploader.upload(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramVideoClient.uploadVideo(uploadMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testUploadVideoWhenExceptionOccurred() {
        when(instagramVideoUploader.upload(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramVideoClient.uploadVideo(uploadMediaRequest());

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static UploadMediaRequest uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }
}
