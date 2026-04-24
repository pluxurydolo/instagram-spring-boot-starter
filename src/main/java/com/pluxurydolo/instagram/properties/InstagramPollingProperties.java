package com.pluxurydolo.instagram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

import java.time.Duration;

@ConfigurationProperties(prefix = "instagram.polling")
public record InstagramPollingProperties(

    @Name("max-repeat")
    int maxRepeat,

    @Name("delay")
    Duration delay
) {
}
