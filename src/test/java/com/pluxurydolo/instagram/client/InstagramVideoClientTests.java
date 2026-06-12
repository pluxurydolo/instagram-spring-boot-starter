package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.PublishMediaRequest;
import com.pluxurydolo.instagram.exception.InstagramVideoPublicationException;
import com.pluxurydolo.instagram.flow.upload.video.InstagramVideoPublisher;
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
    private InstagramVideoPublisher instagramVideoPublisher;

    @InjectMocks
    private InstagramVideoClient instagramVideoClient;

    @Test
    void testPublishVideo() {
        when(instagramVideoPublisher.publish(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramVideoClient.publishVideo(publishMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testPublishVideoWhenExceptionOccurred() {
        when(instagramVideoPublisher.publish(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramVideoClient.publishVideo(publishMediaRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramVideoPublicationException.class));
    }

    private static PublishMediaRequest publishMediaRequest() {
        return new PublishMediaRequest("mediaUrl", "caption");
    }
}
