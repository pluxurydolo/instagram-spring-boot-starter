package com.pluxurydolo.instagram.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(MockitoExtension.class)
class ContainerStatusLoggerTests {
    private static final ContainerStatusLogger STATUS_LOGGER = new ContainerStatusLogger();
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(ContainerStatusLogger.class);


    @Test
    void testProgressChangedWhenUploadStateIsNull() {
        List<ILoggingEvent> logs = listAppender().list;

        STATUS_LOGGER.log("IN_PROGRESS");
        STATUS_LOGGER.log("PROCESSING");
        STATUS_LOGGER.log("FINISHED");
        STATUS_LOGGER.log("ERROR");
        STATUS_LOGGER.log("EXPIRED");
        STATUS_LOGGER.log("FAILED");
        STATUS_LOGGER.log("UNKNOWN");

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(7);

                assertThat(logs.get(0).getFormattedMessage())
                    .isEqualTo("qsfx [instagram-starter] Контейнер создается... (IN_PROGRESS)");
                assertThat(logs.get(1).getFormattedMessage())
                    .isEqualTo("qsfx [instagram-starter] Контейнер создается... (PROCESSING)");
                assertThat(logs.get(2).getFormattedMessage())
                    .isEqualTo("ewoz [instagram-starter] Контейнер создан (FINISHED)");
                assertThat(logs.get(3).getFormattedMessage())
                    .isEqualTo("zkfe [instagram-starter] Произошла ошибка при создании контейнера (ERROR)");
                assertThat(logs.get(4).getFormattedMessage())
                    .isEqualTo("zkfe [instagram-starter] Произошла ошибка при создании контейнера (EXPIRED)");
                assertThat(logs.get(5).getFormattedMessage())
                    .isEqualTo("zkfe [instagram-starter] Произошла ошибка при создании контейнера (FAILED)");
                assertThat(logs.get(6).getFormattedMessage())
                    .isEqualTo("jion [instagram-starter] Статус создания контейнера неизвестен (UNKNOWN)");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }
}
