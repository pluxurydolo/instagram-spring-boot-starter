package com.pluxurydolo.instagram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "instagram.polling")
public record InstagramPollingProperties(
    int maxRepeat,
    Duration delay
) {
}
