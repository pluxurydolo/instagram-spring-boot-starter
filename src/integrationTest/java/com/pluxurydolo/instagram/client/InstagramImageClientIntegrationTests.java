package com.pluxurydolo.instagram.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pluxurydolo.instagram.base.AbstractIntegrationTests;
import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.slf4j.LoggerFactory.getLogger;

class InstagramImageClientIntegrationTests extends AbstractIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(InstagramImageClient.class);

    @Autowired
    private InstagramImageClient instagramImageClient;

    @Test
    void testUploadImage() {
        List<ILoggingEvent> logs = listAppender().list;

        instagramImageClient.uploadImage(uploadMediaRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("avyk [instagram-starter] Изображение успешно опубликовано");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static UploadMediaRequest  uploadMediaRequest() {
        return new UploadMediaRequest("mediaUrl", "caption");
    }
}
