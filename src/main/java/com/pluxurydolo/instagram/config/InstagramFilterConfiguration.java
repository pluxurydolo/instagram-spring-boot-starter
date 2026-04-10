package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.filter.InstagramRateLimitFilter;
import com.pluxurydolo.instagram.filter.InstagramRequestParamValidationFilter;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.properties.InstagramRateLimitProperties;
import com.pluxurydolo.instagram.validator.RequestParamValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class InstagramFilterConfiguration {

    @Bean
    public InstagramRequestParamValidationFilter instagramRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        InstagramProperties instagramProperties
    ) {
        return new InstagramRequestParamValidationFilter(requestParamValidator, instagramProperties);
    }

    @Bean
    public InstagramRateLimitFilter instagramRateLimitingFilter(
        AtomicInteger requestCounter,
        InstagramProperties instagramProperties,
        InstagramRateLimitProperties instagramRateLimitProperties
    ) {
        return new InstagramRateLimitFilter(requestCounter, instagramProperties, instagramRateLimitProperties);
    }

    @Bean
    public AtomicInteger requestCounter() {
        return new AtomicInteger(0);
    }
}
