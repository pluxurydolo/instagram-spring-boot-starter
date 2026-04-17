package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.filter.InstagramRequestParamValidationFilter;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.validator.RequestParamValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramRequestParamValidationFilter instagramRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        InstagramProperties instagramProperties
    ) {
        return new InstagramRequestParamValidationFilter(requestParamValidator, instagramProperties);
    }
}
