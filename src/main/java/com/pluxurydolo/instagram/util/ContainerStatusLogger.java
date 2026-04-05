package com.pluxurydolo.instagram.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerStatusLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerStatusLogger.class);

    public void log(String statusCode) {
        if ("IN_PROGRESS".equals(statusCode) || "PROCESSING".equals(statusCode)) {
            LOGGER.info("qsfx [instagram-starter] Контейнер создается... ({})", statusCode);
        } else if ("FINISHED".equals(statusCode)) {
            LOGGER.info("ewoz [instagram-starter] Контейнер создан ({})", statusCode);
        } else if ("ERROR".equals(statusCode) || "EXPIRED".equals(statusCode) || "FAILED".equals(statusCode)) {
            LOGGER.error("zkfe [instagram-starter] Произошла ошибка при создании контейнера ({})", statusCode);
        } else {
            LOGGER.error("jion [instagram-starter] Статус создания контейнера неизвестен ({})", statusCode);
        }
    }
}
