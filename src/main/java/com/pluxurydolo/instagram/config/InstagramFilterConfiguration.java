package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.filter.RequestParamValidationFilter;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.validator.RequestParamValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramFilterConfiguration {

    @Bean
    public RequestParamValidationFilter requestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        InstagramProperties instagramProperties
    ) {
        return new RequestParamValidationFilter(requestParamValidator, instagramProperties);
    }
}
