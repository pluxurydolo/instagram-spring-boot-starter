package com.pluxurydolo.instagram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "instagram.rate-limit")
public record InstagramRateLimitProperties(int threshold) {
}
