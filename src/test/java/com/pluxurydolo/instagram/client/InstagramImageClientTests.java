package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.PublishMediaRequest;
import com.pluxurydolo.instagram.exception.InstagramImagePublicationException;
import com.pluxurydolo.instagram.flow.upload.image.InstagramImagePublisher;
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
class InstagramImageClientTests {

    @Mock
    private InstagramImagePublisher instagramImagePublisher;

    @InjectMocks
    private InstagramImageClient instagramImageClient;

    @Test
    void testPublishImage() {
        when(instagramImagePublisher.publish(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = instagramImageClient.publishImage(publishMediaRequest());

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testPublishImageWhenExceptionOccurred() {
        when(instagramImagePublisher.publish(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<String> result = instagramImageClient.publishImage(publishMediaRequest());

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(InstagramImagePublicationException.class));
    }

    private static PublishMediaRequest publishMediaRequest() {
        return new PublishMediaRequest("mediaUrl", "caption");
    }
}
